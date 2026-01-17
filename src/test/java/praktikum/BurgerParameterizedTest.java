package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class) // Указываем, что это параметризованный тест
public class BurgerParameterizedTest {

    private Burger burger; // Тестируемый объект

    @Mock
    private Bun bun; // Мок-объект булочки

    @Mock
    private Ingredient ingredient; // Мок-объект ингредиента

    // Параметры для тестов (будут передаваться через конструктор):
    private final String bunName; // Название булочки
    private final float bunPrice; // Цена булочки
    private final IngredientType ingredientType; // Тип ингредиента
    private final String ingredientName; // Название ингредиента
    private final float ingredientPrice; // Цена ингредиента

    // Конструктор с параметрами (вызывается для каждого набора данных)
    public BurgerParameterizedTest(String bunName, float bunPrice,
                                   IngredientType ingredientType, String ingredientName,
                                   float ingredientPrice) {
        // Сохраняем переданные параметры в поля класса
        this.bunName = bunName;
        this.bunPrice = bunPrice;
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
        this.ingredientPrice = ingredientPrice;

        // Инициализируем моки (аннотации @Mock начинают работать)
        MockitoAnnotations.openMocks(this);
        burger = new Burger(); // Создаем новый бургер

        // Настраиваем стабы для моков с использованием параметров:
        // Когда вызывается bun.getName(), возвращаем bunName из параметров
        when(bun.getName()).thenReturn(bunName);
        // Когда вызывается bun.getPrice(), возвращаем bunPrice из параметров
        when(bun.getPrice()).thenReturn(bunPrice);
        // Когда вызывается ingredient.getType(), возвращаем ingredientType из параметров
        when(ingredient.getType()).thenReturn(ingredientType);
        // Когда вызывается ingredient.getName(), возвращаем ingredientName из параметров
        when(ingredient.getName()).thenReturn(ingredientName);
        // Когда вызывается ingredient.getPrice(), возвращаем ingredientPrice из параметров
        when(ingredient.getPrice()).thenReturn(ingredientPrice);
    }

    // Метод, предоставляющий данные для параметризации
    // Каждый массив Object[] - один набор параметров для конструктора
    @Parameterized.Parameters(name = "Булочка: {0} цена {1}, Ингредиент: {3} тип {2} цена {4}")
    public static Collection<Object[]> data() {
        // Возвращаем коллекцию тестовых данных
        return Arrays.asList(new Object[][] {
                // Первый набор: черная булочка с острым соусом
                {"black bun", 100.0f, IngredientType.SAUCE, "hot sauce", 50.0f},
                // Второй набор: белая булочка с котлетой
                {"white bun", 200.0f, IngredientType.FILLING, "cutlet", 100.0f},
                // Третий набор: красная булочка со сметаной
                {"red bun", 300.0f, IngredientType.SAUCE, "sour cream", 150.0f}
        });
    }

    @Test
    public void testGetPrice() {
        // Этот тест запустится 3 раза с разными параметрами
        burger.setBuns(bun); // Устанавливаем булочку (мок с параметризованными данными)
        burger.addIngredient(ingredient); // Добавляем ингредиент (мок с параметризованными данными)

        // Рассчитываем ожидаемую цену по формуле: (булочка * 2) + ингредиент
        float expectedPrice = (bunPrice * 2) + ingredientPrice;

        // Сравниваем ожидаемую цену с фактической из метода getPrice()
        // Этот тест проверяет, что цикл for в getPrice() выполняется хотя бы один раз
        assertEquals("Цена должна корректно рассчитываться по формуле",
                expectedPrice, burger.getPrice(), 0.001);
    }

    @Test
    public void testGetReceipt() {
        // Этот тест тоже запустится 3 раза с разными параметрами
        burger.setBuns(bun); // Устанавливаем булочку
        burger.addIngredient(ingredient); // Добавляем ингредиент

        // Получаем чек из бургера
        String receipt = burger.getReceipt();

        // Проверяем, что чек содержит название булочки (из параметров)
        assertTrue("Чек должен содержать название булочки: " + bunName,
                receipt.contains(bunName));

        // Проверяем, что чек содержит название ингредиента (из параметров)
        assertTrue("Чек должен содержать название ингредиента: " + ingredientName,
                receipt.contains(ingredientName));

        // Проверяем, что чек содержит тип ингредиента в нижнем регистре
        // ingredientType.toString().toLowerCase() преобразует SAUCE → "sauce"
        assertTrue("Чек должен содержать тип ингредиента",
                receipt.contains(ingredientType.toString().toLowerCase()));

        // Проверяем, что чек содержит слово "Price"
        assertTrue("Чек должен содержать слово 'Price'",
                receipt.contains("Price"));
    }
}