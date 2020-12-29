.text 

	main: 
		
		addi $a0, $zero, 0
		sw $a0, 4($gp) 		# array
		addi $s0, $zero, 0 	# i
		
	for: 	slti $t1, $s0, 10
		beq $t1, $zero, exit
		add $a0, $s0, $zero
		jal initialize
		addi $s0, $s0, 1
		j for
	
	addi $s0, $zero, 0 		# Set i back to 0
	
	forTwo:				# Second for loop
		slti $t1, $s0, 10
		beq $t1, $zero, exit
		add $a0, $s0, $zero
		jal decrement
		addi $s0, $s0, 1
		j forTwo
		
	
	initialize: 			# initialize function
		addi $t0, $zero, 10 
		sll $t1, $a0, 2
		add $t1, $t1, $gp 
		sw $t0, 4($t1)
		jr $ra
		
	decrement:			# decrement function
		sll $t1, $a0, 2
		add $t1, $t1, $gp
		sub $t0, $t1, 5
		sw $t0 4($t1)
		
	exit: 	li $v0, 10
		syscall 
				
	