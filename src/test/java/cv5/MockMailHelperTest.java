package cv5;

import cvs.cv5.lab04.DBManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MockMailHelperTest {
  @Test
  public void mockTest() {
    DBManager mockDBManager =  Mockito.mock(
        DBManager.class
    );

    Mockito.verify(mockDBManager, Mockito.times(2))
        .findMail(Mockito.anyInt());
  }


}
