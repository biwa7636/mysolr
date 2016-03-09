# This is benchmark Solrcloud running on my local
# Test A: 2 shards, two replicas
# Test B: 4 shards, no replica

data <- read.csv(text="10 481983 A
50 142013 A
100 104170 A
500 81730 A
1000 82778 A
5000 90460 A
10000 83672 A
50000 84172 A
100000 86180 A
10 260004 B
50 77300 B
100 55729 B
500 37824 B
1000 35784 B
5000 44409 B
10000 57246 B
50000 36640 B
100000 40728 B", sep=" ", col.names=c("batch_size", "milliseconds", "test"))
library(ggplot2)
ggplot(data=data, aes(col=test, x=batch_size, y=milliseconds)) + 
  geom_line() + scale_x_log10() + scale_y_continuous(limits=c(0, 150000)) +
  geom_text(angle=70, check_overlap = TRUE, aes(label=paste(batch_size,",",milliseconds, "ms")))
