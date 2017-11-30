correlationCaise <- function(A,B){
	chunk <-B
	n <- nrow(A)
	r <- rep(1:ceiling(n/chunk), each = chunk)[1:n]
	d <- split(A, r)

	lapply(d, function(x) {
	    cor(x[,"Order"], x[,"Riccardo.s.score"], method="spearman")
	})
}
