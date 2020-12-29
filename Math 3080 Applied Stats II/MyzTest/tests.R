################################################################################
# Kyle Kazemini
# 2020-02-26
################################################################################
# Tests for z-test function in MyzTest package
################################################################################
context("z-test")
################################################################################
# SETUP
################################################################################
library(MyzTest)

x <- rnorm(20)
y <- rnorm(30)
a <- rnorm(500)
r <- NULL
xbar <- mean(x)
ybar <- mean(y)
abar <- mean(a)
n1 <- 20
n2 <- 30
n3 <- 500
sigma <- 2
sigma2 <- NULL
alternative <- "greater"
alternative2 <- "two.sided"
alternative3 <- "less"
conf.level <- 0.95
conf.level2 <- 0.99
conf.level3 <- 0.9
conf.level4 <- 0.8

################################################################################
# Tests
################################################################################

test_that("correct z value", {
  z <- z.test(x, sigma, mu = 0, alternative, conf.level)
  expect_equal(z, 0.3187617)
})

test_that("correct z value again", {
  z <- z.test(y, sigma3, mu = 0, alternative2, conf.level2)
  expect_equal(z, -0.1351885)
})

test_that("correct z value for large set of data", {
  z <- z.test(a, sigma3, mu = 0, alternative2, conf.level2)
  expect_equal(z, -0.7048723)
})

test_that("null sigma value", {
  z <- z.test(x, sigma2, mu = 0, alternative, conf.level)
  expect_error(z)
})

test_that("null data", {
  z <- z.test(r, sigma, mu = 0, alternative, conf.level)
  expect_error(z)
})

test_that("two sided", {
  z <- z.test(x, sigma, mu = 0, alternative2, conf.level)
  expect_equal(z, 0.3187617)
  expect_equivalent(alternative2, "two.sided")
})

test_that("confidence level", {
  z <- z.test(x, sigma, mu = 0, alternative2, conf.level2)
  expect_equal(z, 0.3187617)
  expect_equivalent(conf.level2, 0.99)
})

test_that("low confidence level", {
  z <- z.test(x, sigma, mu = 0, alternative2, conf.level3)
  expect_equal(z, 0.3187617)
  expect_equivalent(conf.level3, 0.9)
})

test_that("null mu value", {
  z <- z.test(x, sigma, mu = NULL, alternative2, conf.level)
  expect_error(z)
})

test_that("confidence level again", {
  z <- z.test(x, sigma, mu = 0, alternative, conf.level4)
  expect_equal(z, 0.3187617)
  expect_equivalent(conf.level4, 0.8)
})

test_that("low confidence level with different alternative", {
  z <- z.test(x, sigma, mu = 0, alternative2, conf.level)
  expect_equal(z, 0.3187617)
  expect_equivalent(alternative2, "two.sided")
  expect_equivalent(conf.level, 0.95)
})

test_that("different hypothesis", {
  z <- z.test(x, sigma, mu = 0, alternative3, conf.level2)
  expect_equal(z, 0.3187617)
  expect_equivalent(alternative3, "less")
})

################################################################################
