package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.dto.response.CreateUserResponse;
import ru.yandex.practicum.filmorate.dal.dto.response.UpdateUserResponse;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.exception.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class UserRepository implements ru.yandex.practicum.filmorate.storage.user.AbstractUserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public CreateUserResponse createUser(User user) {
        int insertResult = jdbcTemplate
                .update("INSERT INTO FILMORATE.USERS(email, login, user_name, birthday) VALUES(?, ?, ?, ?)",
                        user.getEmail(),
                        user.getLogin(),
                        user.getName(),
                        user.getBirthday());

        if (insertResult < 1) {
            throw new ConditionsNotMetException("Some thing went wrong");
        }

        Optional<User> userResp = Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM FILMORATE.USERS WHERE email = ?",
                            userRowMapper,
                            user.getEmail()));

        return UserMapper.mapToCreateUserResponse(userResp.get());
    }

    @Override
    public UpdateUserResponse updateUser(User user) {

        Long id = user.getId();
        String email = user.getEmail();
        String login = user.getLogin();
        String user_name = user.getName();
        String birthday = user.getBirthday().toString();

        String updateSql = """
                UPDATE FILMORATE.USERS
                SET email = ?, login = ?, user_name = ?, birthday = ?
                WHERE id = ?""";

        int updateResult = jdbcTemplate.update(updateSql,  email, login, user_name, birthday, id);

        if (updateResult < 1) {
            throw new NotFoundException("User not found");
        }

        Optional<User> updatedUser = Optional.ofNullable(
                jdbcTemplate.queryForObject("""
                SELECT * FROM FILMORATE.USERS WHERE id=?
                """, userRowMapper, id));

        return UserMapper.mapToUpdateUserResponse(updatedUser.get());
    }

    @Override
    public User findUserById(long user_id) {
        String sql = "SELECT * FROM FILMORATE.USERS WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, user_id);
    }

    @Override
    public Collection<User> getAllUsers() {
        String sql = "SELECT * FROM FILMORATE.USERS";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Collection<User> getFriends(long user_id) {
        String sql =  "SELECT * FROM FILMORATE.USERS " +
            "WHERE ID IN (SELECT FRIEND_ID " +
            "FROM FILMORATE.FRIENDSHIPS " +
            "WHERE FRIENDSHIPS.USER_ID = ?)";

        if (isUserExists(user_id)){
            return jdbcTemplate.query(sql, userRowMapper, user_id);
        }

        throw new NotFoundException("User not found");
    }

    /**
     * Method adds a record for new friends.
     * @param userId the ID of the user who initiates the friendship
     * @param friendId the ID of the user to be added as a friend
     * @return Returns the number of new records.
     * */
    public int addToFriend(long userId, long friendId) {

        String sql = """
                INSERT INTO FILMORATE.FRIENDSHIPS(USER_ID, FRIEND_ID, STATUS, REQUEST_DATE)
                VALUES (?, ?, ?, ?)
                """;

        if (isUserExists(userId) && isUserExists(friendId)){
            return jdbcTemplate.update(sql, userId, friendId, Friendship.NOT_CONFIRMED.toString(), LocalDateTime.now());
        }

        throw new NotFoundException("User not found");
    }

    /**
    * This method returns common friends from database to service layer of the application
     * @see ru.yandex.practicum.filmorate.service.UserService
     * @return a collection of users who are mutual friends for firstUserId and secondUserId
    * */
    @Override
    public Collection<User> getCommonFriends(long firstUserId, long secondUserId) {

        if (isUserExists(firstUserId) && isUserExists(secondUserId)){
            String sql = """
                    SELECT * FROM FILMORATE.USERS
                             WHERE ID IN (SELECT FRIENDSHIPS.FRIEND_ID 
                                          FROM FILMORATE.FRIENDSHIPS 
                                          WHERE USER_ID=?
                                          INTERSECT
                                          SELECT FRIENDSHIPS.FRIEND_ID 
                                          FROM FILMORATE.FRIENDSHIPS 
                                          WHERE USER_ID=?)""";
            return jdbcTemplate.query(sql, userRowMapper, firstUserId, secondUserId);
        }

        throw new NotFoundException("User not found");
    }

    /**
     * Removes a friendship between two users.
     * @param userId the ID of the first user
     * @param friendId the ID of the second user
     * @throws NotFoundException if the friendship does not exist
     * @return Returns the number of deleted records.
     */
    public int removeFromFriend(long userId, long friendId) {
        String sql = """
                DELETE FROM FILMORATE.FRIENDSHIPS
                WHERE USER_ID = ? AND FRIEND_ID = ?
        """;

        if (isUserExists(userId) && isUserExists(friendId)){
            return jdbcTemplate.update(sql, userId, friendId);
        }

        throw new NotFoundException("User not found");
    }

    @Override
    public boolean isUserExists(long id) {
        String sql = "SELECT COUNT(*) FROM FILMORATE.USERS WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, id) > 0;
    }
}