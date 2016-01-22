package unit;

import static org.mockito.Mockito.*;
import static org.fest.assertions.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;


public class TestInfrastructure {

	@Test
	public void shouldSimpleTestsRun() {
		List sut = new ArrayList();
		Object mock = mock(Object.class);
		when(mock.toString()).thenReturn("testString");
		
		sut.add(mock);
		
		assertThat(sut.toString()).contains("testString");
	}

}
