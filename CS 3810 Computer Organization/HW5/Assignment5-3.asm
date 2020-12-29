.text 

	lui $t0, 0 		# First 16 bits are 0
	ori $t0, $t0, 12345
	
	addi $s0, $zero, 123
	addu $s1, $s0, $t0
	
	lui $t1, 0  		# Again, first 16 bits are 0
	ori $t1, $t1, 34567	
	
	addu $s2, $s0 $t1	
	
	lui $t2, 150 		# First 16 bits converted to decimal
	ori $t2, $t2, 46143	# Second 16 bits converted to decimal
	
	addu $s3, $s0, $t2
	
	