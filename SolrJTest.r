data <- read.csv(text="10 481983
50 142013
100 104170
500 81730
1000 82778
5000 90460
10000 83672
50000 84172
100000 86180", sep=" ", col.names=c("batch_size", "milliseconds"))
library(ggplot2)
ggplot(data=data, aes(x=batch_size, y=milliseconds)) + 
  geom_line() + scale_x_log10() + scale_y_continuous(limits=c(0, 150000)) +
  geom_text(angle=70, check_overlap = TRUE, aes(label=paste(batch_size,",",milliseconds, "ms"))) + 
  geom_("Indexing Time vs BatchSize")

