package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
    }
	
	    @Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
    }
	@Test
	    public void postParameter() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1110"))
			.andExpect(model().attribute("operand1", "111"));
    }

    // Design: Question 1 - Three new test cases for the binary web application

    @Test
    public void testInvalidBinaryInput() throws Exception {
        this.mvc.perform(post("/").param("operand1", "abc").param("operator", "+").param("operand2", "101"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "Invalid binary input")); // Update expected result
    }

    @Test
    public void testEmptyOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1", "").param("operator", "+").param("operand2", "101"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "Invalid binary input")); // Update expected result
    }

    @Test
    public void testMultipleAdditions() throws Exception {
        this.mvc.perform(post("/").param("operand1", "111").param("operator", "+").param("operand2", "111"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1110"));

        this.mvc.perform(post("/").param("operand1", "1110").param("operator", "+").param("operand2", "101"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "10011"));
    }

    // Design: Question 4 - Covering almost all possible cases for implemented operations

    // Test cases for Binary Multiplication (*)
    @Test
    public void testBinaryMultiplication() throws Exception {
        this.mvc.perform(post("/").param("operand1", "101").param("operator", "*").param("operand2", "11"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1111"));
    }

    @Test
    public void testBinaryMultiplicationWithZero() throws Exception {
        this.mvc.perform(post("/").param("operand1", "101").param("operator", "*").param("operand2", "0"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "0"));
    }

    @Test
    public void testBinaryMultiplicationLarge() throws Exception {
        this.mvc.perform(post("/").param("operand1", "111").param("operator", "*").param("operand2", "101"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "100011"));
    }

    @Test
    public void testBinaryMultiplicationInvalidOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1", "abc").param("operator", "*").param("operand2", "11"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "Invalid binary input"));
    }

    @Test
    public void testBinaryMultiplicationEmptyOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1", "").param("operator", "*").param("operand2", "101"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "Invalid binary input"));
    }

    // Test cases for Binary AND (&)
    @Test
    public void testBinaryAND() throws Exception {
        this.mvc.perform(post("/").param("operand1", "101").param("operator", "&").param("operand2", "111"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "101"));
    }

    @Test
    public void testBinaryANDWithZero() throws Exception {
        this.mvc.perform(post("/").param("operand1", "111").param("operator", "&").param("operand2", "000"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "0"));
    }

    @Test
    public void testBinaryANDComplex() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1010").param("operator", "&").param("operand2", "0101"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "0"));
    }

    @Test
    public void testBinaryANDInvalidOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1", "abc").param("operator", "&").param("operand2", "11"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "Invalid binary input"));
    }

    @Test
    public void testBinaryANDEmptyOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1", "").param("operator", "&").param("operand2", "101"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "Invalid binary input"));
    }

    // Test cases for Binary OR (|)
    @Test
    public void testBinaryOR() throws Exception {
        this.mvc.perform(post("/").param("operand1", "101").param("operator", "|").param("operand2", "111"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "111"));
    }

    @Test
    public void testBinaryORWithZero() throws Exception {
        this.mvc.perform(post("/").param("operand1", "101").param("operator", "|").param("operand2", "0"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "101"));
    }

    @Test
    public void testBinaryORComplex() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1100").param("operator", "|").param("operand2", "1010"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1110"));
    }

    @Test
    public void testBinaryORInvalidOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1", "abc").param("operator", "|").param("operand2", "11"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "Invalid binary input"));
    }

    @Test
    public void testBinaryOREmptyOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1", "").param("operator", "|").param("operand2", "101"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "Invalid binary input"));
    }

}
