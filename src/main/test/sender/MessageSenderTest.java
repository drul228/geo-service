package sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderTest {
    @Test
    void testRussionText() {
        GeoServiceImpl gsi = Mockito.spy(GeoServiceImpl.class);
        LocalizationServiceImpl lsi = Mockito.spy(LocalizationServiceImpl.class);
        MessageSenderImpl msi = new MessageSenderImpl(gsi, lsi);
        Map<String, String> map = new HashMap<String, String>();
        map.put("x-real-ip", "172.0.32.11");
        Assert.assertEquals(msi.send(map), "Добро пожаловать");
    }

    @Test
    void testEnglishText() {
        GeoServiceImpl gsi = Mockito.spy(GeoServiceImpl.class);
        LocalizationServiceImpl lsi = Mockito.spy(LocalizationServiceImpl.class);
        MessageSenderImpl msi = new MessageSenderImpl(gsi, lsi);
        Map<String, String> map = new HashMap<String, String>();
        map.put("x-real-ip", "96.44.183.149");
        Assert.assertEquals(msi.send(map), "Welcome");


    }

    @ParameterizedTest
    @ValueSource(strings = {"172.18.32.11", "172.123.123.12"})
    void testGeoByIp(String ip) {
        GeoServiceImpl gsi = Mockito.spy(GeoServiceImpl.class);
        Assert.assertTrue(gsi.byIp(ip).getCountry() == Country.RUSSIA);

    }

    @Test
    void testLocal() {
        LocalizationServiceImpl lsi = Mockito.spy(LocalizationServiceImpl.class);
        Assert.assertEquals(lsi.locale(Country.RUSSIA), "Добро пожаловать");

    }
}
