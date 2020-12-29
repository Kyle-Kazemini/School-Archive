#'Math 3080 Term Project
#'Spring 2020
#'Kyle Kazemini
#'February 25, 2020

#First, import and view the data, as well as some useful libraries
library(leaps)
library(stats)

setwd("/Users/kylekazemini/Downloads")
county_outcomes <- read.csv("county_outcomes.csv")

#Check if it's in the format we want
is.data.frame(county_outcomes)

View(county_outcomes)


l <- sapply(county_outcomes, function(x) is.factor(x))
m <- county_outcomes[, l]
ifelse(sapply(m, function(x) length(levels(x)) == 1, "DROP”, “NODROP"))


#Select variables using leaps library
reg <- regsubsets(as.matrix(response) ~ as.factor(m), data = county_outcomes, nbest = 2, really.big = TRUE)
plot(reg, scale = "adjr2")
summary(reg)


#Establish a model
linearModel <- lm(kir_white_pooled_mean ~ par_rank_white_pooled_mean + par_rank_white_pooled_mean_se, data = county_outcomes)
selectedModel <- step(linearModel)
summary(selectedModel)


#Graph the model
plot(selectedModel)


#T statistic
t <- summary(selectedModel)$t.value
t

#T value
pt(0.95, df = 3217)


#Other plots
plot(county_outcomes$kir_pooled_female_n, main = "Pooled Female", ylab = "")
plot(county_outcomes$kir_pooled_male_n, main = "Pooled Male", ylab = "")



###############################################################################################
#Practice Code


mean(county_outcomes$kir_natam_female_p1, na.rm = TRUE)

response <- county_outcomes['kir_white_pooled_mean']
predictors <- county_outcomes[, !names(county_outcomes) %in% "kir_white_pooled_mean"]
predictors2 <- na.omit(predictors)

fit <- lm(county_outcomes$kir_white_pooled_mean ~ county_outcomes$par_rank_white_male_mean)
print(fit)
summary(fit)$r.squared
summary(fit)$adj.r.squared


modelSelection <- lm(county_outcomes$jail_pooled_female_p100 ~ . - 1, data = county_outcomes)
model1 <- glm(county_outcomes$kir_natam_female_p100 ~ . - 1, data = county_outcomes)

scatter.smooth(county_outcomes$par_rank_white_male_mean, county_outcomes$kir_white_pooled_mean)

