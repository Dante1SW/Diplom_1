package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BurgerTest {

    private Burger burger; // Тестируемый объект

    @Mock
    private Bun bun; // Мок-объект булочки

    @Mock
    private Ingredient ingredient1; // Мок-объект первого ингредиента

    @Mock
    private Ingredient ingredient2; // Мок-объект второго ингредиента

    @Before
    public void setUp() {
        // Инициализация моков (аннотации @Mock начинают работать)
        MockitoAnnotations.openMocks(this);
        burger = new Burger(); // Создаем новый бургер перед каждым тестом

        // Настройка стабов (задаем поведение моков):
        // Когда вызывается bun.getName(), возвращаем "black bun"
        when(bun.getName()).thenReturn("black bun");
        // Когда вызывается bun.getPrice(), возвращаем 100.0f
        when(bun.getPrice()).thenReturn(100.0f);
        // Когда вызывается ingredient1.getType(), возвращаем SAUCE
        when(ingredient1.getType()).thenReturn(IngredientType.SAUCE);
        // Когда вызывается ingredient1.getName(), возвращаем "hot sauce"
        when(ingredient1.getName()).thenReturn("hot sauce");
        // Когда вызывается ingredient1.getPrice(), возвращаем 50.0f
        when(ingredient1.getPrice()).thenReturn(50.0f);
        // Аналогично для ingredient2
        when(ingredient2.getType()).thenReturn(IngredientType.FILLING);
        when(ingredient2.getName()).thenReturn("cutlet");
        when(ingredient2.getPrice()).thenReturn(200.0f);
    }

    @Test
    public void testSetBuns() {
        // Тестируем метод setBuns()
        burger.setBuns(bun); // Устанавливаем булочку в бургер
        // Проверяем, что булочка действительно установилась
        // assertSame проверяет, что это один и тот же объект в памяти
        assertSame("Булочка должна быть установлена в бургер", bun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        // Тестируем метод addIngredient()
        burger.addIngredient(ingredient1); // Добавляем ингредиент в бургер
        // Проверяем, что список ингредиентов содержит 1 элемент
        // assertEquals сравнивает ожидаемое значение (1) с фактическим (размер списка)
        assertEquals("После добавления должен быть 1 ингредиент", 1, burger.ingredients.size());
    }

    @Test
    public void testRemoveIngredient() {
        // Тестируем метод removeIngredient()
        burger.addIngredient(ingredient1); // Сначала добавляем ингредиент
        burger.removeIngredient(0); // Удаляем его по индексу 0
        // Проверяем, что список ингредиентов стал пустым
        // assertTrue проверяет, что условие истинно
        assertTrue("Список ингредиентов должен быть пустым после удаления", burger.ingredients.isEmpty());
    }

    @Test
    public void testMoveIngredient() {
        // Тестируем метод moveIngredient()
        burger.addIngredient(ingredient1); // Добавляем первый ингредиент
        burger.addIngredient(ingredient2); // Добавляем второй ингредиент
        burger.moveIngredient(0, 1); // Перемещаем элемент с позиции 0 на позицию 1
        // Проверяем, что после перемещения осталось 2 ингредиента
        assertEquals("После перемещения должно остаться 2 ингредиента", 2, burger.ingredients.size());
    }

    @Test
    public void testGetPriceWithoutIngredients() {
        // Тестируем getPrice() без ингредиентов (проверяем цикл for с пустым списком)
        burger.setBuns(bun); // Устанавливаем булочку
        // Не добавляем ингредиенты - проверяем случай пустого списка
        // Ожидаемая цена: булочка (100) * 2 = 200
        // Третий параметр 0.001 - допустимая погрешность при сравнении float
        assertEquals("Цена без ингредиентов должна быть цена булочки * 2",
                200.0f, burger.getPrice(), 0.001);
    }
}