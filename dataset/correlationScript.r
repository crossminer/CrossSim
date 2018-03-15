correlationCaise <- function(A,B){
	chunk <-B
	n <- nrow(A)
	r <- rep(1:ceiling(n/chunk), each = chunk)[1:n]
	data <- split(A, r)


	lapply(data, function(x) {
	    cor(x[,"Order"], x[,"Riccardo.s.score"], method="spearman")
	})
}
