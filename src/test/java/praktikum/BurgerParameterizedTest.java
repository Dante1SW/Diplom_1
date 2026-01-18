package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.assertj.core.api.SoftAssertions;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BurgerParameterizedTest {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    private final String bunName;
    private final float bunPrice;
    private final IngredientType ingredientType1;
    private final String ingredientName1;
    private final float ingredientPrice1;
    private final IngredientType ingredientType2;
    private final String ingredientName2;
    private final float ingredientPrice2;
    private final float expectedTotalPrice;

    public BurgerParameterizedTest(
            String bunName,
            float bunPrice,
            IngredientType ingredientType1,
            String ingredientName1,
            float ingredientPrice1,
            IngredientType ingredientType2,
            String ingredientName2,
            float ingredientPrice2,
            float expectedTotalPrice) {

        this.bunName = bunName;
        this.bunPrice = bunPrice;
        this.ingredientType1 = ingredientType1;
        this.ingredientName1 = ingredientName1;
        this.ingredientPrice1 = ingredientPrice1;
        this.ingredientType2 = ingredientType2;
        this.ingredientName2 = ingredientName2;
        this.ingredientPrice2 = ingredientPrice2;
        this.expectedTotalPrice = expectedTotalPrice;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();

        when(mockBun.getName()).thenReturn(bunName);
        when(mockBun.getPrice()).thenReturn(bunPrice);
        when(mockIngredient1.getType()).thenReturn(ingredientType1);
        when(mockIngredient1.getName()).thenReturn(ingredientName1);
        when(mockIngredient1.getPrice()).thenReturn(ingredientPrice1);
        when(mockIngredient2.getType()).thenReturn(ingredientType2);
        when(mockIngredient2.getName()).thenReturn(ingredientName2);
        when(mockIngredient2.getPrice()).thenReturn(ingredientPrice2);
    }

    @Parameterized.Parameters(name = "Тест {index}: {0} + {2} {3} + {5} {6} = {8}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"black bun", 100.0f,
                        IngredientType.SAUCE, "hot sauce", 100.0f,
                        IngredientType.FILLING, "cutlet", 100.0f,
                        400.0f},

                {"white bun", 200.0f,
                        IngredientType.SAUCE, "sour cream", 200.0f,
                        IngredientType.FILLING, "dinosaur", 200.0f,
                        800.0f},

                {"red bun", 300.0f,
                        IngredientType.SAUCE, "chili sauce", 300.0f,
                        IngredientType.FILLING, "sausage", 300.0f,
                        1200.0f},

                {"special bun", 50.0f,
                        IngredientType.SAUCE, "free sauce", 0.0f,
                        IngredientType.FILLING, "free filling", 0.0f,
                        100.0f}
        });
    }

    @Test
    public void getPriceParameterizedTest() {
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(burger.getPrice())
                .as("Общая цена должна быть корректно рассчитана")
                .isEqualTo(expectedTotalPrice);
        softAssertions.assertThat(burger.getPrice())
                .as("Цена должна быть неотрицательной")
                .isGreaterThanOrEqualTo(0.0f);
        softAssertions.assertAll();
    }

    @Test
    public void getReceiptContentParameterizedTest() {
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        String receipt = burger.getReceipt();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(receipt)
                .as("Чек должен содержать название булочки: " + bunName)
                .contains(bunName);
        softAssertions.assertThat(receipt)
                .as("Чек должен содержать тип первого ингредиента в нижнем регистре")
                .contains(ingredientType1.toString().toLowerCase());
        softAssertions.assertThat(receipt)
                .as("Чек должен содержать название первого ингредиента")
                .contains(ingredientName1);
        softAssertions.assertThat(receipt)
                .as("Чек должен содержать тип второго ингредиента в нижнем регистре")
                .contains(ingredientType2.toString().toLowerCase());
        softAssertions.assertThat(receipt)
                .as("Чек должен содержать название второго ингредиента")
                .contains(ingredientName2);
        softAssertions.assertThat(receipt)
                .as("Чек должен содержать общую цену")
                .contains("Price:");
        softAssertions.assertAll();
    }

    @Test
    public void receiptFormatAndStructureParameterizedTest() {
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        String receipt = burger.getReceipt();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(receipt)
                .as("Первая строка должна содержать булочку в скобках")
                .contains("(==== " + bunName + " ====)");
        softAssertions.assertThat(receipt)
                .as("Чек должен содержать булочку в конце")
                .contains("(==== " + bunName + " ====)");
        softAssertions.assertThat(receipt)
                .as("Последняя строка должна содержать слово Price")
                .contains("Price:");
        softAssertions.assertAll();
    }
}