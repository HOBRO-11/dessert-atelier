package com.yangnjo.dessert_atelier.domain;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.vale_type.Destination;
import com.yangnjo.dessert_atelier.repository.AddressRepository;
import com.yangnjo.dessert_atelier.repository.BasketRepository;
import com.yangnjo.dessert_atelier.repository.CartRepository;
import com.yangnjo.dessert_atelier.repository.ComponentRepository;
import com.yangnjo.dessert_atelier.repository.DeliveryCompanyRepository;
import com.yangnjo.dessert_atelier.repository.DeliveryRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductImageRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.OptionRepository;
import com.yangnjo.dessert_atelier.repository.OrderCartRepository;
import com.yangnjo.dessert_atelier.repository.OrderRepository;
import com.yangnjo.dessert_atelier.repository.ProductQuantityRepository;
import com.yangnjo.dessert_atelier.repository.ProductRepository;
import com.yangnjo.dessert_atelier.repository.QnARepository;
import com.yangnjo.dessert_atelier.repository.RecipeRepository;
import com.yangnjo.dessert_atelier.repository.ReviewImageRepository;
import com.yangnjo.dessert_atelier.repository.ReviewRepository;
import com.yangnjo.dessert_atelier.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@ActiveProfiles("h2-test")
@Transactional
public class DomainTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private AddressRepository addressesRepository;
    @Autowired
    private ComponentRepository componentsRepository;
    @Autowired
    private RecipeRepository recipesRepository;
    @Autowired
    private ProductRepository productsRepository;
    @Autowired
    private ProductQuantityRepository productQuantityRepository;
    @Autowired
    private OptionRepository optionsRepository;
    @Autowired
    private DisplayProductImageRepository displayProductImagesRepository;
    @Autowired
    private DisplayProductRepository displayProductsRepository;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private QnARepository qnaRepository;
    @Autowired
    private ReviewImageRepository reviewImagesRepository;
    @Autowired
    private ReviewRepository reviewsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository ordersRepository;
    @Autowired
    private OrderCartRepository orderCartsRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DeliveryCompanyRepository deliveryCompanyRepository;

    @Test
    void domain_test() {

        // User Side ok

        Users users = new Users("testEmail@email.com", "asdf1234!@#$", "testUser", 1234_4567);
        usersRepository.save(users);

        Addresses addresses = new Addresses("testAddress", "48765", "102-1804", "testReceiver", 8765_4321, true);
        users.addAddress(addresses);
        addressesRepository.save(addresses);

        // Product Side ok

        Components components = new Components("testCompetent");
        componentsRepository.save(components);

        Recipes recipes = new Recipes(components, 5);

        Products products = new Products("testProduct", 100_000, "testThumb.jpg", "testProductComment",
                ProductStatus.AVAILABLE);
        products.addRecipes(recipes);

        productsRepository.save(products);
        recipesRepository.save(recipes);

        // Display Product Side ok

        DisplayProductImages displayProductImages = new DisplayProductImages(List.of("dpImage1.jpg", "dpImage2.jpg"));
        displayProductImagesRepository.save(displayProductImages);

        DisplayProducts displayProducts = new DisplayProducts("testTitle", 100_000, "dpThumb.jpg", "testDpDesc",
                displayProductImages, SalePolicyStatus.NORMAL, DisplayProductStatus.SALE);

        ProductQuantity productQuantities = new ProductQuantity(products, 10);
        productQuantityRepository.save(productQuantities);

        Options options = new Options(5, "testOptionDesc", 500_000, List.of(productQuantities));
        displayProducts.addOption(options);

        displayProductsRepository.save(displayProducts);
        optionsRepository.save(options);

        // Order Side

        Carts cart = new Carts(displayProducts, options, 1, CartStatus.USED);
        cartRepository.save(cart);

        Basket basket = new Basket(List.of(cart));
        users.setBasket(basket);
        basketRepository.save(basket);

        OrderCarts orderCarts = new OrderCarts(List.of(cart));
        orderCartsRepository.save(orderCarts);

        Orders userOrder = Orders.createUserOrder("ORDER_CODE_123", users,
                new Destination("48765", "102-1804", "testReceiver", 8765_4321), orderCarts);
        ordersRepository.save(userOrder);

        Orders guestOrder = Orders.createGuestOrder("ORDER_CODE_124", "qwer1234",
                new Destination("48765", "102-1804", "testReceiver", 8765_4321), orderCarts);
        ordersRepository.save(guestOrder);

        // Delivery Side

        DeliveryCompany deliveryCompany = new DeliveryCompany("testDeliveryCompany", 987_7654);
        deliveryCompanyRepository.save(deliveryCompany);

        Deliveries delivery = new Deliveries("DELIVERY_CODE_123", deliveryCompany);
        userOrder.setDelivery(delivery);
        deliveryRepository.save(delivery);

        // React Side

        QnAs qna = QnAs.createUserQnA(displayProducts, users, "testQnaContent");
        qnaRepository.save(qna);

        QnAs guestQnA = QnAs.createGuestQnA(displayProducts, "qewr1234", "testGuestComment");
        qnaRepository.save(guestQnA);

        ReviewImages reviewImages = new ReviewImages(List.of("reviewImage1.jpg", "reviewImage2.jpg"));
        reviewImagesRepository.save(reviewImages);

        Reviews review = Reviews.createUserReviews(displayProducts, users, reviewImages, "testReview");
        reviewsRepository.save(review);

        Reviews guestReview = Reviews.createStoreReviews(displayProducts, reviewImages, "testStoreReview",
                ReviewOrigin.NAVER_STORE);
        reviewsRepository.save(guestReview);
    }
}
