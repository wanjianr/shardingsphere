package com.app.worktest;


import com.app.worktest.pro.controller.NhsaController;
import com.app.worktest.pro.dto.response.BaseResponseDTO;
import com.app.worktest.pro.dto.token.TokenRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2026/3/24
 * <p>UPDATE DATE: 2026/3/24
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@SpringBootTest
public class NhsaTest {

    @Autowired
    public NhsaController nhsaController;


    @Test
    public void testGetToken() {

        TokenRequestDTO tokenRequestDTO = TokenRequestDTO.builder()
                .admdvs("100000")
                .psnCertType("01")
                .certno("210682199912120912")
                .psnName("王开疆").mob("15612345678")
                .agentPsnCertType("01")
                .agentCertno("210682198011014067")
                .agentPsnName("赵德胜")
                .agentMob("15612345678")
                .adultFlag(true)
                .channel("92")
                .claimType("0").build();
        ResponseEntity<BaseResponseDTO<String>> controllerToken = nhsaController.getToken(tokenRequestDTO);

        System.out.println("token: " + controllerToken.getBody().getData());

        ResponseEntity<BaseResponseDTO<String>> responseEntity = nhsaController.executeAuthFlow(controllerToken.getBody().getData(), "2026", "01");
        System.out.println("executeAuthFlow: " + responseEntity.getBody().getData());
    }
}
