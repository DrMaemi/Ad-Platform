package com.ssg.backendpreassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.backendpreassignment.config.RestDocsConfig;
import com.ssg.backendpreassignment.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfig.class)
public class ApiTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("01. 상품 정보 리스트 생성")
    void addProducts() throws Exception {
        List<ProductReqDto> productReqDtos = new ArrayList<>();

        productReqDtos.add(ProductReqDto.builder()
                .companyName("이마트")
                .productName("허쉬 초코멜로쿠키 45g")
                .price(600L)
                .stock(10L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("신세계백화점")
                .productName("크리스피롤 12 곡 180g")
                .price(2000L)
                .stock(5L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("삼성전자")
                .productName("갤럭시 22 자급제모델")
                .price(1200000L)
                .stock(0L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("이마트")
                .productName("말랑카우 핸드워시 250ml")
                .price(2600L)
                .stock(6L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("신세계백화점")
                .productName("삼립 미니꿀호떡 322g")
                .price(1200L)
                .stock(4L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("이마트")
                .productName("피코크 어랑손만두 어랑만두 700g")
                .price(6400L)
                .stock(2L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("신세계백화점")
                .productName("빼빼로바 아몬드아이스크림 4입")
                .price(2800L)
                .stock(1L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("이마트")
                .productName("피코크 에이 클래스 우유 900ml")
                .price(2500L)
                .stock(3L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("이마트")
                .productName("아삭달콤 방울토마토 500g")
                .price(4500L)
                .stock(0L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("이마트")
                .productName("[롯데삼강] 돼지바 (70ml*6 입)")
                .price(3000L)
                .stock(1L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("나이키")
                .productName("나이키 덩크로우 흰검")
                .price(129000L)
                .stock(4L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("스타벅스")
                .productName("이달의 원두 500g")
                .price(18500L)
                .stock(4L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("스타벅스")
                .productName("아쿠아머그")
                .price(23000L)
                .stock(7L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("삼성전자")
                .productName("삼성전자 43 인치 스마트모니터")
                .price(480000L)
                .stock(2L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("나이키")
                .productName("나이키 헤리티지 스우시 캡")
                .price(25000L)
                .stock(5L)
                .build());

        this.mockMvc.perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productReqDtos))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("POST_api-products",
                                requestFields(
                                        fieldWithPath("[]").description("An array of products to add"),
                                        fieldWithPath("[].companyName").description("Name of company which produces the product"),
                                        fieldWithPath("[].productName").description("Product name"),
                                        fieldWithPath("[].price").description("Product price"),
                                        fieldWithPath("[].stock").description("Product stock")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("The number of records created")
                                )
                        )
                );
    }

    @Test
    @DisplayName("02. 상품 정보 리스트 생성 시 유효성 검사")
    void addProductsValidation() throws Exception {
        List<ProductReqDto> productReqDtos = new ArrayList<>();

        productReqDtos.add(ProductReqDto.builder()
                .productName("허쉬 초코멜로쿠키 45g")
                .price(600L)
                .stock(-10L)
                .build());
        productReqDtos.add(ProductReqDto.builder()
                .companyName("신세계백화점")
                .price(-2000L)
                .stock(5L)
                .build());

        this.mockMvc.perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productReqDtos))
                )
                .andExpect(status().isBadRequest())
                .andDo(
                        document("POST_api-products-validation",
                                requestFields(
                                        fieldWithPath("[]").description("An array of products to add"),
                                        fieldWithPath("[].companyName").description("Name of company which produces the product").optional(),
                                        fieldWithPath("[].productName").description("Product name").optional(),
                                        fieldWithPath("[].price").description("Product price").optional(),
                                        fieldWithPath("[].stock").description("Product stock").optional()
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of validation result"),
                                        fieldWithPath("result.[].field").description("The field which is invalid"),
                                        fieldWithPath("result.[].message").description("Description message"),
                                        fieldWithPath("result.[].rejectedValue").description("The value rejected").optional() // 'rejectedValue': null일 수 있음
                                )
                        )
                );
    }

    @Test
    @DisplayName("03. 상품 정보 리스트 조회")
    void getProducts() throws Exception {
        this.mockMvc.perform(
                        get("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("GET_api-products",
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of products"),
                                        fieldWithPath("result.[].id").description("Product's ID"),
                                        fieldWithPath("result.[].companyName").description("Company ID who produces the product"),
                                        fieldWithPath("result.[].productName").description("Product name"),
                                        fieldWithPath("result.[].price").description("Product price"),
                                        fieldWithPath("result.[].stock").description("Product stock remained"),
                                        fieldWithPath("result.[].createdDate").description("Date when the record created"),
                                        fieldWithPath("result.[].lastModifiedDate").description("Last date when the record modified")
                                )
                        )
                );
    }

    @Test
    @DisplayName("04. 업체 등록")
    void registerCompany() throws Exception {
        CompanyReqDto companyReqDto = CompanyReqDto.builder()
                .companyName("이마트")
                .businessRegistrationNumber("123-45-67890")
                .phoneNumber("010-123-4567")
                .address("주소")
                .build();

        this.mockMvc.perform(
                        post("/api/company")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(companyReqDto))
                )
                .andExpect(status().isOk())
                .andDo(
                        document("POST_api-company-register",
                                requestFields(
                                        fieldWithPath("companyName").description("Company name which is appeared in product list"),
                                        fieldWithPath("businessRegistrationNumber").description("Company's business registration number"),
                                        fieldWithPath("phoneNumber").description("Company's phone number"),
                                        fieldWithPath("address").description("Company's address")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Processed company info"),
                                        fieldWithPath("result.id").description("Processed company's ID"),
                                        fieldWithPath("result.companyName").description("Processed company's name"),
                                        fieldWithPath("result.businessRegistrationNumber").description("Processed company's business registration number"),
                                        fieldWithPath("result.phoneNumber").description("Processed company's phone number"),
                                        fieldWithPath("result.address").description("Processed company's address")
                                )
                        )
                );
    }

    @Test
    @DisplayName("05. 업체 등록 시 유효성 검사")
    void registerCompanyValidation() throws Exception {
        CompanyReqDto companyReqDto = CompanyReqDto.builder()
                .companyName("등록되지 않은 업체 이름")
                .businessRegistrationNumber("123456-78901")
                .phoneNumber("010-1234567")
                .build();

        this.mockMvc.perform(
                        post("/api/company")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(companyReqDto))
                )
                .andExpect(status().isBadRequest())
                .andDo(
                        document("POST_api-company-validation",
                                requestFields(
                                        fieldWithPath("companyName").description("Company name which is not appeared in product list"),
                                        fieldWithPath("businessRegistrationNumber").description("Company's business registration number"),
                                        fieldWithPath("phoneNumber").description("Company's phone number"),
                                        fieldWithPath("address").description("Company's address")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of validation result"),
                                        fieldWithPath("result.[].field").description("The field which is invalid"),
                                        fieldWithPath("result.[].message").description("Description message"),
                                        fieldWithPath("result.[].rejectedValue").description("The value rejected").optional()
                                )
                        )
                );
    }

    @Test
    @DisplayName("06. 업체 Master 정보 리스트 조회")
    void getCompanies() throws Exception {
        this.mockMvc.perform(
                        get("/api/companies")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo( // REST docs 문서 작성
                        document("GET_api-companies",
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP stats"),
                                        fieldWithPath("result").description("An array of companies' info"),
                                        fieldWithPath("result.[].id").description("Company's ID"),
                                        fieldWithPath("result.[].name").description("Company's name"),
                                        fieldWithPath("result.[].businessRegistrationNumber").optional().description("Company's business registration number"),
                                        fieldWithPath("result.[].phoneNumber").optional().description("Company's phone number"),
                                        fieldWithPath("result.[].address").optional().description("Company's address"),
                                        fieldWithPath("result.[].createdDate").description("Date when the record created"),
                                        fieldWithPath("result.[].lastModifiedDate").description("Last date when the record modified"),
                                        fieldWithPath("result.[].address").description("Last date when the record modified"),
                                        fieldWithPath("result.[].productDtos.[]").description("An array of products registered according to the company"),
                                        fieldWithPath("result.[].productDtos.[].id").description("Product ID"),
                                        fieldWithPath("result.[].productDtos.[].companyDto").description("Company who registered the product(it should be null because it is duplicated info)"),
                                        fieldWithPath("result.[].productDtos.[].productName").description("Product name"),
                                        fieldWithPath("result.[].productDtos.[].price").description("Product price"),
                                        fieldWithPath("result.[].productDtos.[].stock").description("Product stock"),
                                        fieldWithPath("result.[].productDtos.[].createdDate").description("Date when the product registered"),
                                        fieldWithPath("result.[].productDtos.[].lastModifiedDate").description("Last date when the product info modified")
                                )
                        )
                );
    }

    @Test
    @DisplayName("07. 업체정보 중 업체명을 제외한 채 수정")
    void updateCompanyExceptName() throws Exception {
        CompanyReqDto companyReqDto = CompanyReqDto.builder()
                .companyName("이마트")
                .phoneNumber("010-2345-6789")
                .address("수정된 주소")
                .build();

        this.mockMvc.perform(
                        patch("/api/company")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(companyReqDto))
                )
                .andExpect(status().isOk())
                .andDo(
                        document("POST_api-company-update",
                                requestFields(
                                        fieldWithPath("companyName").description("Company name which is registered in the repo"),
                                        fieldWithPath("businessRegistrationNumber").description("Company's business registration number to update. It would not be updated if the value is null."),
                                        fieldWithPath("phoneNumber").description("Company's phone number to update. It would not be updated if the value is null"),
                                        fieldWithPath("address").description("Company's address to update. It would not be updated if the value is null")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Updated company info"),
                                        fieldWithPath("result.id").description("Updated company's ID"),
                                        fieldWithPath("result.companyName").description("Updated company's name"),
                                        fieldWithPath("result.businessRegistrationNumber").description("Updated company's business registration number"),
                                        fieldWithPath("result.phoneNumber").description("Updated company's phone number"),
                                        fieldWithPath("result.address").description("Updated company's address")
                                )
                        )
                );
    }

    @Test
    @DisplayName("08. 계약 생성")
    void createContract() throws Exception {
        ContractReqDto contractReqDto = ContractReqDto.builder()
                .companyId(1000000001L)
                .build();

        this.mockMvc.perform(
                post("/api/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractReqDto))
        )
                .andExpect(status().isCreated())
                .andDo(
                        document("POST_api-contract-create",
                                requestFields(
                                        fieldWithPath("companyId").description("Company's ID to create contract")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Contracted company's info"),
                                        fieldWithPath("result.id").description("Created contract ID"),
                                        fieldWithPath("result.companyId").description("Company ID who created the contract"),
                                        fieldWithPath("result.startDate").description("Date when the contract created"),
                                        fieldWithPath("result.endDate").description("Date when the contract would be expired")
                                )
                        )
                );
    }

    @Test
    @DisplayName("09. 계약 생성 시 유효성 검사 1")
    void createContractValidation1() throws Exception {
        ContractReqDto contractReqDto = ContractReqDto.builder()
                .companyId(1000000001L)
                .build();

        this.mockMvc.perform(
                        post("/api/contract")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(contractReqDto))
                )
                .andExpect(status().isBadRequest())
                .andDo(
                        document("POST_api-contract-validation1",
                                requestFields(
                                        fieldWithPath("companyId").description("Company's ID to create contract")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of validation result"),
                                        fieldWithPath("result.[].field").description("The field which is invalid"),
                                        fieldWithPath("result.[].message").description("Description message"),
                                        fieldWithPath("result.[].rejectedValue").description("The value rejected")
                                )
                        )
                );
    }

    @Test
    @DisplayName("09. 계약 생성 시 유효성 검사 2")
    void createContractValidation2() throws Exception {
        ContractReqDto contractReqDto = ContractReqDto.builder()
                .companyId(1000000002L)
                .build();

        this.mockMvc.perform(
                        post("/api/contract")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(contractReqDto))
                )
                .andExpect(status().isBadRequest())
                .andDo(
                        document("POST_api-contract-validation2",
                                requestFields(
                                        fieldWithPath("companyId").description("Company's ID to create contract")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of validation result"),
                                        fieldWithPath("result.[].field").description("The field which is invalid"),
                                        fieldWithPath("result.[].message").description("Description message"),
                                        fieldWithPath("result.[].rejectedValue").description("The value rejected")
                                )
                        )
                );
    }

    @Test
    @DisplayName("10. 계약 리스트 조회")
    void getContracts() throws Exception {
        this.mockMvc.perform(
                        get("/api/contracts")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("GET_api-contracts",
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of contract list"),
                                        fieldWithPath("result.[].id").description("Contract ID"),
                                        fieldWithPath("result.[].companyId").description("Company ID which created the contract"),
                                        fieldWithPath("result.[].startDate").description("Date when the contract"),
                                        fieldWithPath("result.[].endDate").description("Date when the contract would be expired"),
                                        fieldWithPath("result.[].createdDate").description("Date when the record created"),
                                        fieldWithPath("result.[].lastModifiedDate").description("Last date when the record modified")
                                )
                        )
                );
    }

    @Test
    @DisplayName("11. 광고입찰 생성 1")
    void createAdBid1() throws Exception {
        AdBidReqDto adBidReqDto = AdBidReqDto.builder()
                .companyId(1000000001L)
                .productId(1000000001L)
                .bidPrice(100L)
                .build();

        this.mockMvc.perform(
                        post("/api/ad/bid")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(adBidReqDto))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("POST_api-ad-bid-create1",
                                requestFields(
                                        fieldWithPath("companyId").description("Company ID to create AD bid"),
                                        fieldWithPath("productId").description("Product ID to create AD bid"),
                                        fieldWithPath("bidPrice").description("Bid price the company offered")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Created AD Bid info"),
                                        fieldWithPath("result.id").description("AD Bid ID"),
                                        fieldWithPath("result.companyId").description("Company ID which created the bid"),
                                        fieldWithPath("result.productId").description("ID of the product which would be displayed when the company won the bid"),
                                        fieldWithPath("result.bidPrice").description("Registered bid price when the bid created"),
                                        fieldWithPath("result.createdDate").description("Date when the record created"),
                                        fieldWithPath("result.lastModifiedDate").description("Last date when the record modified")
                                )
                        )
                );
    }

    @Test
    @DisplayName("12. 광고입찰 생성 2")
    void createAdBid2() throws Exception {
        AdBidReqDto adBidReqDto = AdBidReqDto.builder()
                .companyId(1000000001L)
                .productId(1000000004L)
                .bidPrice(250L)
                .build();

        this.mockMvc.perform(
                        post("/api/ad/bid")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(adBidReqDto))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("POST_api-ad-bid-create2",
                                requestFields(
                                        fieldWithPath("companyId").description("Company ID to create AD bid"),
                                        fieldWithPath("productId").description("Product ID to create AD bid"),
                                        fieldWithPath("bidPrice").description("Bid price the company offered")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Created AD Bid info"),
                                        fieldWithPath("result.id").description("AD Bid ID"),
                                        fieldWithPath("result.companyId").description("Company ID which created the bid"),
                                        fieldWithPath("result.productId").description("ID of the product which would be displayed when the company won the bid"),
                                        fieldWithPath("result.bidPrice").description("Registered bid price when the bid created"),
                                        fieldWithPath("result.createdDate").description("Date when the record created"),
                                        fieldWithPath("result.lastModifiedDate").description("Last date when the record modified")
                                )
                        )
                );
    }

    @Test
    @DisplayName("13. 광고입찰 생성 3")
    void createAdBid3() throws Exception {
        AdBidReqDto adBidReqDto = AdBidReqDto.builder()
                .companyId(1000000001L)
                .productId(1000000009L)
                .bidPrice(350L)
                .build();

        this.mockMvc.perform(
                        post("/api/ad/bid")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(adBidReqDto))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("POST_api-ad-bid-create3",
                                requestFields(
                                        fieldWithPath("companyId").description("Company ID to create AD bid"),
                                        fieldWithPath("productId").description("Product ID to create AD bid"),
                                        fieldWithPath("bidPrice").description("Bid price the company offered")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Created AD Bid info"),
                                        fieldWithPath("result.id").description("AD Bid ID"),
                                        fieldWithPath("result.companyId").description("Company ID which created the bid"),
                                        fieldWithPath("result.productId").description("ID of the product which would be displayed when the company won the bid"),
                                        fieldWithPath("result.bidPrice").description("Registered bid price when the bid created"),
                                        fieldWithPath("result.createdDate").description("Date when the record created"),
                                        fieldWithPath("result.lastModifiedDate").description("Last date when the record modified")
                                )
                        )
                );
    }

    @Test
    @DisplayName("14. 광고입찰 생성 유효성 검사")
    void createAdBidValidation() throws Exception {
        AdBidReqDto adBidReqDto = AdBidReqDto.builder()
                .companyId(1000000002L)
                .productId(1000000001L)
                .bidPrice(10L)
                .build();

        this.mockMvc.perform(
                        post("/api/ad/bid")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(adBidReqDto))
                )
                .andExpect(status().isBadRequest())
                .andDo(
                        document("POST_api-ad-bid-validation",
                                requestFields(
                                        fieldWithPath("companyId").description("Company ID to create AD bid"),
                                        fieldWithPath("productId").description("Product ID to create AD bid"),
                                        fieldWithPath("bidPrice").description("Bid price the company offered")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of validation result"),
                                        fieldWithPath("result.[].field").description("The field which is invalid"),
                                        fieldWithPath("result.[].message").description("Description message"),
                                        fieldWithPath("result.[].rejectedValue").description("The value rejected")
                                )
                        )
                );
    }

    @Test
    @DisplayName("15. 광고전시 리스트 조회")
    void getAds() throws Exception {
        this.mockMvc.perform(
                        get("/api/ad/display")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("GET_api-ad-display",
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of AD bids that bid price of each bid is TOP 3"),
                                        fieldWithPath("result.[].bidId").description("AD Bid ID").optional(),
                                        fieldWithPath("result.[].companyId").description("Company ID which created the bid").optional(),
                                        fieldWithPath("result.[].productId").description("Product ID of the AD Bid").optional().optional(),
                                        fieldWithPath("result.[].bidPrice").description("Registered bid price").optional()
                                )
                        )
                );
    }

    @Test
    @DisplayName("16. 광고과금 생성 1")
    void createAdCharge1() throws Exception {
        AdChargeReqDto adChargeReqDto = AdChargeReqDto.builder()
                .bidId(1000000001L)
                .build();

        this.mockMvc.perform(
                        post("/api/ad/charge")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(adChargeReqDto))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("POST_api-ad-charge-create1",
                                requestFields(
                                        fieldWithPath("bidId").description("AD Bid ID to create charge")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Created AD charge info"),
                                        fieldWithPath("result.id").description("AD charge ID").optional(),
                                        fieldWithPath("result.bidId").description("AD Bid ID").optional(),
                                        fieldWithPath("result.clickedDate").description("Date when the AD charge created which can be understood as the time user clicked the AD").optional().optional(),
                                        fieldWithPath("result.bidPrice").description("Registered bid price according to the AD").optional()
                                )
                        )
                );
    }

    @Test
    @DisplayName("17. 광고과금 생성 2")
    void createAdCharge2() throws Exception {
        AdChargeReqDto adChargeReqDto = AdChargeReqDto.builder()
                .bidId(1000000002L)
                .build();

        this.mockMvc.perform(
                        post("/api/ad/charge")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(adChargeReqDto))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("POST_api-ad-charge-create2",
                                requestFields(
                                        fieldWithPath("bidId").description("AD Bid ID to create charge")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Created AD charge info"),
                                        fieldWithPath("result.id").description("AD charge ID").optional(),
                                        fieldWithPath("result.bidId").description("AD Bid ID").optional(),
                                        fieldWithPath("result.clickedDate").description("Date when the AD charge created which can be understood as the time user clicked the AD").optional().optional(),
                                        fieldWithPath("result.bidPrice").description("Registered bid price according to the AD").optional()
                                )
                        )
                );
    }

    @Test
    @DisplayName("18. 광고과금 생성 3")
    void createAdCharge3() throws Exception {
        AdChargeReqDto adChargeReqDto = AdChargeReqDto.builder()
                .bidId(1000000001L)
                .build();

        this.mockMvc.perform(
                        post("/api/ad/charge")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(adChargeReqDto))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("POST_api-ad-charge-create3",
                                requestFields(
                                        fieldWithPath("bidId").description("AD Bid ID to create charge")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Created AD charge info"),
                                        fieldWithPath("result.id").description("AD charge ID").optional(),
                                        fieldWithPath("result.bidId").description("AD Bid ID").optional(),
                                        fieldWithPath("result.clickedDate").description("Date when the AD charge created which can be understood as the time user clicked the AD").optional().optional(),
                                        fieldWithPath("result.bidPrice").description("Registered bid price according to the AD").optional()
                                )
                        )
                );
    }

    @Test
    @DisplayName("19. 광고과금 생성 4")
    void createAdCharge4() throws Exception {
        AdChargeReqDto adChargeReqDto = AdChargeReqDto.builder()
                .bidId(1000000002L)
                .build();

        this.mockMvc.perform(
                        post("/api/ad/charge")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(adChargeReqDto))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("POST_api-ad-charge-create4",
                                requestFields(
                                        fieldWithPath("bidId").description("AD Bid ID to create charge")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("Created AD charge info"),
                                        fieldWithPath("result.id").description("AD charge ID").optional(),
                                        fieldWithPath("result.bidId").description("AD Bid ID").optional(),
                                        fieldWithPath("result.clickedDate").description("Date when the AD charge created which can be understood as the time user clicked the AD").optional().optional(),
                                        fieldWithPath("result.bidPrice").description("Registered bid price according to the AD").optional()
                                )
                        )
                );
    }

    @Test
    @DisplayName("20. 광고과금 유효성 검사")
    void createAdChargeValidation() throws Exception {
        AdChargeReqDto adChargeReqDto = AdChargeReqDto.builder()
                .bidId(10000000010L)
                .build();

        this.mockMvc.perform(
                        post("/api/ad/charge")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(adChargeReqDto))
                )
                .andExpect(status().isBadRequest())
                .andDo(
                        document("POST_api-ad-charge-validation",
                                requestFields(
                                        fieldWithPath("bidId").description("AD Bid ID to create charge")
                                ),
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of validation result"),
                                        fieldWithPath("result.[].field").description("The field which is invalid"),
                                        fieldWithPath("result.[].message").description("Description message"),
                                        fieldWithPath("result.[].rejectedValue").description("The value rejected")
                                )
                        )
                );
    }

    @Test
    @DisplayName("21. 광고과금정산 리스트 조회")
    void getAdChargeCals() throws Exception {
        this.mockMvc.perform(
                        get("/api/ad/charge/cals")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("GET_api-ad-charge-cals",
                                responseFields(
                                        fieldWithPath("timestamp").description("API requested time"),
                                        fieldWithPath("code").description("HTTP status code"),
                                        fieldWithPath("status").description("HTTP status"),
                                        fieldWithPath("result").description("An array of calculated charges"),
                                        fieldWithPath("result.[].clickedDate").description("The date when charges are calculated"),
                                        fieldWithPath("result.[].bidId").description("AD bid ID to charge"),
                                        fieldWithPath("result.[].companyId").description("Company ID who created the AD bid"),
                                        fieldWithPath("result.[].companyName").description("Company name who created the AD bid"),
                                        fieldWithPath("result.[].productId").description("Product ID displayed by AD"),
                                        fieldWithPath("result.[].productName").description("Product name displayed by AD"),
                                        fieldWithPath("result.[].cntClicked").description("Total counts of clicked"),
                                        fieldWithPath("result.[].totalCharge").description("Total charge(total count * AD bid price)")
                                )
                        )
                );
    }
}
