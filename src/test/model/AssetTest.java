package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssetTest {

    @Mock
    Asset mockAsset;

    @Test
    void getSymbol() {
        Assertions.assertNotNull(mockAsset);
        when(mockAsset.getSymbol()).thenReturn("");
        Assertions.assertEquals("", mockAsset.getSymbol());
    }
    @Test
    void getCompany() {
        Assertions.assertNotNull(mockAsset);
        when(mockAsset.getCompany()).thenReturn("");
        Assertions.assertEquals("", mockAsset.getCompany());
    }
    @Test
    void getExchange() {
        Assertions.assertNotNull(mockAsset);
        when(mockAsset.getExchange()).thenReturn("");
        Assertions.assertEquals("", mockAsset.getExchange());
    }
    @Test
    void getLatestAssetHistory() {
        Assertions.assertNotNull(mockAsset);
        when(mockAsset.getLatestAssetHistory()).thenReturn(new AssetHistory("null", 0));
        Assertions.assertEquals(new AssetHistory("null", 0), mockAsset.getLatestAssetHistory());
    }
    @Test
    void getAssetHistoryList() {
        Assertions.assertNotNull(mockAsset);
        when(mockAsset.getAssetHistoryList()).thenReturn(new ArrayList<>());
        Assertions.assertEquals(new ArrayList<>(), mockAsset.getAssetHistoryList());
    }
}