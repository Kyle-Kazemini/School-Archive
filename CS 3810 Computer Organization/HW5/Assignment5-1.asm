.data # Data region

input: .asciiz "n = "
newLine: .asciiz "\n"
space: .asciiz " "


.text # Text region

	la $a0, input
	li $v0, 4 
	syscall 		# Print ascii to standard out

	li $v0, 5 
	syscall 		# Read integer input
	add $s0, $zero, $v0 	# Store n in $s0


	addi $t0, $zero, 0 	# i is stored in $t0
	addi $t2, $zero, 1	# j is stored in $t2
	addi $t4, $zero, 1	# Used for incrementing
	
For: 	slt $t1, $t0, $s0	# Check if i < n
	beq $t1, $zero, Exit
	addi $t0, $t0, 1 	# Increment i
	add $t5, $t0, $t4	# Calculate i + 1
	li $t2, 1		# Reset j to 1
	li $v0, 4       
	la $a0, newLine        	
	syscall			# Print a new line
	jal InnerFor
	
InnerFor:
	slt $t3, $t2, $t5	# Check if j <= i using i + 1 value calculated earlier
	beq $t3, $zero, For	
	add $a0, $zero, $t2	
	li $v0, 1		
	syscall			# Print number
	la $a0, space
	li $v0, 4 
	syscall 		# Print a space between each number
	addi $t2, $t2, 1	# Increment j
	jal InnerFor

Exit:	
	




