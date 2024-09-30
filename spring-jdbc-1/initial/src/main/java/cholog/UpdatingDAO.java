package cholog;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class UpdatingDAO {
    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
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

    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public void insert(Customer customer) {
        //todo: customer를 디비에 저장하기
        // 도대체 왜 안되는거지??
      jdbcTemplate.update("insert into customers (first_name, last_name) values (?, ?)", customer.getFirstName(), customer.getLastName());

    }
    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public int delete(Long id) {
        return jdbcTemplate.update("delete from customers where id = ?", Long.valueOf(id));
    }

    /**
     * public int update(final PreparedStatementCreator psc, final KeyHolder generatedKeyHolder)
     */
    public Long insertWithKeyHolder(Customer customer) {
        String sql = "insert into customers (first_name, last_name) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //todo : keyHolder에 대해 학습하고, Customer를 저장후 저장된 Customer의 id를 반환하기

        jdbcTemplate.update(connection -> {
            PreparedStatement ps =connection.prepareStatement(sql,new String[]{"id"});
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            return ps;
        }
        , keyHolder);

        Long id = keyHolder.getKey().longValue();

        return keyHolder.getKey().longValue();
    }
}
