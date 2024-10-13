package com.yangnjo.dessert_atelier.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPresetImage;
import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.display_product.ProductQuantity;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain.member.Address;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.order.Basket;
import com.yangnjo.dessert_atelier.domain.order.BasketProperty;
import com.yangnjo.dessert_atelier.domain.order.OptionQuantity;
import com.yangnjo.dessert_atelier.domain.order.OptionQuantityStatus;
import com.yangnjo.dessert_atelier.domain.order.Orders;
import com.yangnjo.dessert_atelier.domain.product.Component;
import com.yangnjo.dessert_atelier.domain.product.ComponentUnit;
import com.yangnjo.dessert_atelier.domain.product.Product;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.domain.product.Recipe;
import com.yangnjo.dessert_atelier.domain.react.QnA;
import com.yangnjo.dessert_atelier.domain.react.Review;
import com.yangnjo.dessert_atelier.domain.react.ReviewImage;
import com.yangnjo.dessert_atelier.domain.react.ReviewOrigin;
import com.yangnjo.dessert_atelier.domain.value_type.Destination;
import com.yangnjo.dessert_atelier.repository.AddressRepository;
import com.yangnjo.dessert_atelier.repository.BasketRepository;
import com.yangnjo.dessert_atelier.repository.ComponentRepository;
import com.yangnjo.dessert_atelier.repository.DeliveryCompanyRepository;
import com.yangnjo.dessert_atelier.repository.DeliveryRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductImageRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.MemberRepository;
import com.yangnjo.dessert_atelier.repository.OptionRepository;
import com.yangnjo.dessert_atelier.repository.OqRepository;
import com.yangnjo.dessert_atelier.repository.OrderRepository;
import com.yangnjo.dessert_atelier.repository.ProductQuantityRepository;
import com.yangnjo.dessert_atelier.repository.ProductRepository;
import com.yangnjo.dessert_atelier.repository.QnARepository;
import com.yangnjo.dessert_atelier.repository.RecipeRepository;
import com.yangnjo.dessert_atelier.repository.ReviewImageRepository;
import com.yangnjo.dessert_atelier.repository.ReviewRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@ActiveProfiles("h2-test")
// @Transactional
public class DomainTest {

        @PersistenceContext
        private EntityManager em;
        @Autowired
        private MemberRepository memberRepository;
        @Autowired
        private AddressRepository addressRepository;
        @Autowired
        private ComponentRepository componentRepository;
        @Autowired
        private RecipeRepository recipeRepository;
        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private ProductQuantityRepository productQuantityRepository;
        @Autowired
        private OptionRepository optionRepository;
        @Autowired
        private DisplayProductImageRepository displayProductImageRepository;
        @Autowired
        private DisplayProductRepository displayProductRepository;
        @Autowired
        private BasketRepository basketRepository;
        @Autowired
        private QnARepository qnaRepository;
        @Autowired
        private ReviewImageRepository reviewImageRepository;
        @Autowired
        private ReviewRepository reviewRepository;
        @Autowired
        private OqRepository oqRepository;
        @Autowired
        private OrderRepository orderRepository;
        @Autowired
        private DeliveryRepository deliveryRepository;
        @Autowired
        private DeliveryCompanyRepository deliveryCompanyRepository;
        @Autowired
        private DisplayProductPresetRepository displayProductPresetRepository;

        @Test
        @Transactional
        void domain_test() throws InterruptedException {

                // User Side ok

                Member users = new Member("testEmail@email.com", "asdf1234!@#$", "testUser", "01012344567",
                                MemberOrigin.STORE);
                memberRepository.save(users);

                Address addresses = new Address("testAddress", "48765", "102-1804", "testReceiver", "01087654321",
                                true);
                users.addAddress(addresses);
                addressRepository.save(addresses);

                // Product Side ok

                Component components = new Component("testCompetent", ComponentUnit.GRAM);
                componentRepository.save(components);

                Recipe recipes = new Recipe(components, 5);

                Product products = new Product("testProduct", 100_000, "testThumb.jpg", ProductStatus.AVAILABLE);
                products.addRecipes(List.of(recipes));

                productRepository.save(products);
                recipeRepository.save(recipes);

                // Display Product Side ok

                DisplayProduct displayProducts = new DisplayProduct("testNaming", "testDpDesc", "testthumb.jpg",
                                SaleStatus.ON_SALE);
                displayProductRepository.save(displayProducts);

                DisplayProductPresetImage displayProductImages = new DisplayProductPresetImage(
                                Map.of("dpImage1.jpg", "dpImage1.jpg", "dpImage2.jpg", "dpImage2.jpg"));
                displayProductImageRepository.save(displayProductImages);

                DisplayProductPreset displayProductPreset = DisplayProductPreset.createDefaultDPP(displayProducts,
                                "testNaming", "testThumb.jpg", "testTitle", 100_000, 1, "testContent",
                                displayProductImages);
                displayProductPresetRepository.save(displayProductPreset);
                displayProducts.setCurrentDppId(displayProductPreset.getId());

                ProductQuantity productQuantities = new ProductQuantity(products, 10);
                Option options = new Option(5, "testOptionDesc", 500_000, List.of(productQuantities), 1);
                displayProductPreset.addOptions(List.of(options));

                optionRepository.save(options);
                productQuantityRepository.save(productQuantities);

                // Order Side

                Basket basket = new Basket(users);
                basket.addProperty(new BasketProperty(1L, LocalDateTime.now(), List.of(1L, 2L), 1));
                Thread.sleep(500);
                basket.addProperty(new BasketProperty(1L, LocalDateTime.now(), List.of(1L, 2L), 1));
                basketRepository.save(basket);

                Orders userOrder = Orders.createUserOrder("ORDER_CODE_123", users,
                                new Destination("48765", "102-1804", "testReceiver", "01087654321"),
                                100_000L);

                Orders guestOrder = Orders.createGuestOrder("ORDER_CODE_124", "qwer1234",
                                new Destination("48765", "102-1804", "testReceiver", "01087654321"),
                                100_000L);

                OptionQuantity oq = new OptionQuantity(userOrder, List.of(options), displayProductPreset, 1,
                                OptionQuantityStatus.PAID);

                OptionQuantity oq1 = new OptionQuantity(userOrder, List.of(options), displayProductPreset, 1,
                                OptionQuantityStatus.PAID);

                OptionQuantity oq2 = new OptionQuantity(guestOrder, List.of(options), displayProductPreset, 1,
                                OptionQuantityStatus.PAID);

                OptionQuantity oq3 = new OptionQuantity(userOrder, List.of(options), displayProductPreset, 1,
                                OptionQuantityStatus.PAID);

                orderRepository.save(userOrder);
                orderRepository.save(guestOrder);
                oqRepository.save(oq);
                oqRepository.save(oq1);
                oqRepository.save(oq2);
                oqRepository.save(oq3);
                // Delivery Side

                DeliveryCompany deliveryCompany = new DeliveryCompany("testDeliveryCompany", "0519877654");
                deliveryCompanyRepository.save(deliveryCompany);

                Delivery delivery = new Delivery("DELIVERY_CODE_123", deliveryCompany);
                userOrder.setDelivery(delivery);
                deliveryRepository.save(delivery);

                // React Side

                QnA qna = QnA.createMemberQnA(displayProducts, users, "testQnaContent");
                qnaRepository.save(qna);

                QnA guestQnA = QnA.createGuestQnA(displayProducts, "qewr1234", "testGuestComment");
                qnaRepository.save(guestQnA);

                ReviewImage reviewImages = new ReviewImage(
                                Map.of("reviewImage1.jpg", "reviewImage1.jpg", "reviewImage2.jpg",
                                                "reviewImage2.jpg"));
                reviewImageRepository.save(reviewImages);

                ReviewImage guestReviewImages = new ReviewImage(
                                Map.of("reviewImage1.jpg", "reviewImage1.jpg", "reviewImage2.jpg",
                                                "reviewImage2.jpg"));
                reviewImageRepository.save(guestReviewImages);

                Review review = Review.createUserReviews(displayProducts, users, reviewImages,
                                List.of(options), 5, "testReview");
                reviewRepository.save(review);

                Review guestReview = Review.createStoreReviews(displayProducts, guestReviewImages,
                                List.of(options), 5, "testGuestReview",
                                ReviewOrigin.NAVER_STORE);
                reviewRepository.save(guestReview);

        }
}
