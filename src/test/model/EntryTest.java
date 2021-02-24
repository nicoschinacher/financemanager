package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntryTest {

    @Mock
    Entry mockEntry;

    @Test
    void getDate() {
        Assertions.assertNotNull(mockEntry);
        when(mockEntry.getDate()).thenReturn("");
        Assertions.assertEquals("", mockEntry.getDate());
    }
    @Test
    void getEntryAction() {
        Assertions.assertNotNull(mockEntry);
        when(mockEntry.getEntryAction()).thenReturn(EntryAction.BUY);
        Assertions.assertEquals(EntryAction.BUY, mockEntry.getEntryAction());
    }
    @Test
    void getNumber() {
        Assertions.assertNotNull(mockEntry);
        when(mockEntry.getNumber()).thenReturn(0);
        Assertions.assertEquals(0, mockEntry.getNumber());
    }
    @Test
    void getPrice() {
        Assertions.assertNotNull(mockEntry);
        when(mockEntry.getPrice()).thenReturn(0);
        Assertions.assertEquals(0, mockEntry.getPrice());
    }
    @Test
    void getAsset() {
        Assertions.assertNotNull(mockEntry);
        when(mockEntry.getAsset()).thenReturn(null);
        Assertions.assertNull(mockEntry.getAsset());
    }
}