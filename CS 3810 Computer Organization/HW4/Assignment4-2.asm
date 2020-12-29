.data

     askForInput:  .asciiz "Enter n \n "
     displayResult: .asciiz "%d"

.text

	main:
		# Ask for input
		la $a0, askForInput
		li $v0, 4
		syscall 
		
		# Record input
		li $v0, 5
		syscall
		move $a0, $v0
		
		# Adjust stack, store $ra
		addi $sp, $sp, -4
		sw $ra, 0($sp)
		
		# Call series function
		jal series
		
		# Adjust stack, store $ra
		addi $sp, $sp, 4
		lw $ra 0($sp)
		
		# Store return value
		move $s0, $v0		
		
		# Print result
		addi $t0, $zero, 1
		la $a0, ($t0)
		li  $v0, 1
		syscall
		
		# End
		li $v0, 10
    		syscall
    		
	series:
		#Adjust stack
		addi $sp, $sp, -12
		sw $ra, 0($sp)
		sw $a0, 4($sp)
		sw $s1, 8($sp)
		
		slti $t0, $a0, 2	# test for n <= 1
		bne $t0, $zero, else
		addi $v0, $zero, 1	# return 1

		lw $a0, 0($sp) 		# restore argument n
		lw $ra, 4($sp)		# restore the return address
		lw $s1, 8($sp) 		
		addi $sp, $sp, 12	# adjust stack pointer
		
		j exit			# return to caller
	
	
	else: 	
		addi $a0, $a0, -1 	# (n - 1)
		jal series 		
		
		addi $a0, $a0, -1	# (n - 2)
		addi $t1, $zero, 2
		mult $t1, $v0	
		mflo $s1
		#jal series		
		
		
	exit:
		li $v0, 10
		syscall
		
		
