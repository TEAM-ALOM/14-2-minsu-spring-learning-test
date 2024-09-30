package cholog;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QueryingDAO {
    private JdbcTemplate jdbcTemplate;

    public QueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> actorRowMapper = (resultSet, rowNum) -> {
        Customer customer = new Customer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
        return customer;
    };
    // 추후 rowMapper에 대해 학습해보고 이용해보기


    /**
     * public <T> T queryForObject(String sql, Class<T> requiredType)
     */
    public int count() {
        //TODO : customers 디비에 포함되어있는 row가 몇개인지 확인하는 기능 구현
        Integer count = jdbcTemplate.queryForObject("select count(*) from customers", Integer.class);
        return count;
    }

    /**
     * public <T> T queryForObject(String sql, Class<T> requiredType, @Nullable Object... args)
     */
    public String getLastName(Long id) {
        String lastName = jdbcTemplate.queryForObject("select last_name from customers where id = ?", String.class, id);
        return lastName;
    }

    /**
     * public <T> T queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    public Customer findCustomerById(Long id) {
        String sql = "select id, first_name, last_name from customers where id = ?";
        return jdbcTemplate.queryForObject(
                sql, actorRowMapper, id);
    }

    /**
     * public <T> List<T> query(String sql, RowMapper<T> rowMapper)
     */
    public List<Customer> findAllCustomers() { // 뭐가 뭔지 모르겠지만 readMe파일 참고하면서 일단 .. 썸바리헬프미
        String sql = "select id, first_name, last_name from customers";

        final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
            Customer customer = new Customer(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"));
            return customer;
        };

        return jdbcTemplate.query(sql,actorRowMapper);
    }

    /**
     * public <T> List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    public List<Customer> findCustomerByFirstName(String firstName) {
        String sql = "select id, first_name, last_name from customers where first_name = ?";
        //TODO : firstName을 기준으로 customer를 list형태로 반환
        //readme에 없지만 일단
         return jdbcTemplate.query(sql,actorRowMapper,firstName);

    }
}
