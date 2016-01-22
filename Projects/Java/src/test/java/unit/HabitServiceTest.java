/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import java.sql.SQLException;
import org.junit.Test;
import ru.agilecamp.habitator.HabitsService;
import static org.mockito.Mockito.*;

/**
 *
 * @author d_alex
 */
public class HabitServiceTest {
    @Test
    public void shouldAddHabit() throws SQLException {
        DataSourceMockBuilder dataSourceMockBuilder = new DataSourceMockBuilder();

        HabitsService habitService = new HabitsService(dataSourceMockBuilder.getDataSourceMock());

        habitService.addHabit(1,"testHabit");

        verify(dataSourceMockBuilder.getPreparedStatement()).execute();
    }
}
