package mockExample;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class MockExample {


    @Test
    public void doMockVerify() {
        // mock creation
        List mockedList = Mockito.mock(List.class);

        // using mock object - it does not throw any "unexpected interaction" exception
        mockedList.add("one");
        mockedList.clear();

        // selective, explicit, highly readable verification
        Mockito.verify(mockedList).add("one"); // 驗證有沒有跑過 mockedList.add("one");
        Mockito.verify(mockedList).clear();  // 驗證有沒有跑過 mockedList.clear();
    }

    @Test
    public void doMock_stub_method_calls() {
        // mock creation
        List mockedList = Mockito.mock(List.class);

        Mockito.when(mockedList.get(0)).thenReturn("first");

        // the following prints "first"
        assertEquals("first", mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        assertEquals(null, mockedList.get(999));

    }


}
