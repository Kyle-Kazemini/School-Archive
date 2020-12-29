# You can learn more about package authoring with RStudio at:
#
#   http://r-pkgs.had.co.nz/
#
# Some useful keyboard shortcuts for package authoring:
#
#   Install Package:           'Cmd + Shift + B'
#   Check Package:             'Cmd + Shift + E'
#   Test Package:              'Cmd + Shift + T'

#'@export
#'Perform a z-test
#'
#'@param x - data
#'@param sigma - standard deviation
#'@param mu - mean
#'@param alternative - alternative hypothesis
#'@param conf.level - confidence level
#'
#'Output: the value of a simple z-test:
#'z <- (xbar - mu) / (sigma / sqrt(n))
#'
z.test <- function(x, sigma = NULL, mu = 0, alternative = "two.sided",
                   conf.level = 0.95) {
  rval <- list()

  # I will do this part: understanding the following line requires
  # metaprogramming knowledge
  rval$data.name <- deparse(substitute(x))

  if (is.null(sigma)) {
    sigma <- sd(x)
  }

  xbar <- mean(x)
  n <- length(x)
  z<- (xbar - mu) / (sigma / sqrt(n))
  stderr <- sigma / sqrt(n)
  estimate <- mean(x)
  conf.int <- confint(x, sigma, level = 0.95)
  null.value <- NULL
  alternative <- alternative
  method <- "z.test"

  rval$statistic <- c(statistic = z)
  rval$parameter <- c(sigma = sigma)  # I'll also do parameter
  rval$conf.int <- c(conf.int = conf.int)
  rval$estimate <- c(estimate = estimate)
  rval$null.value <- c(null.value = null.value)
  rval$stderr <- c(stderr = stderr)
  rval$alternative <- c(alternative = alternative)
  rval$method <- c(method = method)

  class(rval) <- "htest"
  return(str(rval))
}
