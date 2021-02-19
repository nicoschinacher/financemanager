package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntryTest {

    @Test
    void getDate() {
        Entry entry = new Entry(
                new Asset(
                        "TSLA",
                        "Tesla",
                        "NASDAQ"
                ),
                "2020-12-19",
                EntryAction.BUY,
                10,
                10
        );
        Assertions.assertEquals("2020-12-19", entry.getDate());
    }
    @Test
    void getEntryAction() {
        Entry entry = new Entry(
                new Asset(
                        "TSLA",
                        "Tesla",
                        "NASDAQ"
                ),
                "2020-12-19",
                EntryAction.BUY,
                10,
                10
        );
        Assertions.assertEquals(EntryAction.BUY, entry.getEntryAction());
    }
    @Test
    void getNumber() {
        Entry entry = new Entry(
                new Asset(
                        "TSLA",
                        "Tesla",
                        "NASDAQ"
                ),
                "2020-12-19",
                EntryAction.BUY,
                10,
                10
        );
        Assertions.assertEquals(10, entry.getNumber());
    }
    @Test
    void getPrice() {
        Entry entry = new Entry(
                new Asset(
                        "TSLA",
                        "Tesla",
                        "NASDAQ"
                ),
                "2020-12-19",
                EntryAction.BUY,
                10,
                10
        );
        Assertions.assertEquals(10, entry.getPrice());
    }
}