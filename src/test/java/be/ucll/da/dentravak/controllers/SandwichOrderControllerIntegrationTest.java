package be.ucll.da.dentravak.controllers;

import be.ucll.da.Application;
import be.ucll.da.Model.BreadType;
import be.ucll.da.Model.Sandwich;
import be.ucll.da.Model.Order;
import be.ucll.da.dentravak.model.SandwichTestBuilder;
import be.ucll.da.Database.OrderRepository;
import be.ucll.da.Database.SandwichRepository;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static be.ucll.da.dentravak.model.SandwichOrderTestBuilder.aOrder;
import static be.ucll.da.dentravak.model.SandwichTestBuilder.aSandwich;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SandwichOrderControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private SandwichRepository sandwichRepository;
    @Autowired
    private OrderRepository orderRepository;

    private Sandwich savedSandwich;

    @Before
    public void setUpASavedSandwich() {
        sandwichRepository.deleteAll();
        orderRepository.deleteAll();
        savedSandwich = sandwichRepository.save(SandwichTestBuilder.aSandwich().withName("Americain").withIngredients("vlees").withPrice(3.5).build());
    }

    @Test
    public void testGetSandwichOrders_NoOrdersSaved_EmptyList() throws JSONException {
        String actualSandwiches = httpGet("/orders");
        String expectedSandwiches = "[]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

    @Test
    public void testPostSandwichOrder() throws JSONException {
        Order sandwichOrder = aOrder().forSandwich(savedSandwich).withBreadType(BreadType.BOTERHAMMEKES).withMobilePhoneNumber("0487/123456").build();


        String actualSandwichOrder = httpPost("/orders", sandwichOrder);
        String expectedSandwichOrder = "{\"id\":\"${json-unit.ignore}\",\"sandwichId\":\"" + savedSandwich.getId() + "\",\"name\":\"Americain\",\"breadType\":\"BOTERHAMMEKES\",\"creationDate\":\"${json-unit.ignore}\",\"price\":3.5,\"mobilePhoneNumber\":\"0487/123456\"}";

        assertThatJson(actualSandwichOrder).isEqualTo(expectedSandwichOrder);
    }

    @Test
    public void testGetSandwichOrders_WithOrdersSaved_ReturnsListWithOrders() throws JSONException {
        //Sandwich sandwich = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();
        Order sandwichOrder = aOrder().forSandwich(savedSandwich).withBreadType(BreadType.BOTERHAMMEKES).withMobilePhoneNumber("0487/123456").build();
        Order order_with_id = orderRepository.save(sandwichOrder);

        String actualOrders = httpGet("/orders");
        String expectedOrders = "[{\"id\":\"${json-unit.ignore}\",\"sandwichId\":\"" + savedSandwich.getId() + "\",\"name\":\"Americain\",\"breadType\":\"BOTERHAMMEKES\",\"creationDate\":\"${json-unit.ignore}\",\"price\":3.5,\"mobilePhoneNumber\":\"0487/123456\"}]";

        assertThatJson(actualOrders).isEqualTo(expectedOrders);

        //throw new RuntimeException("Implement this test and then the production code");
    }

}