.data
		# Use the .align directive to align data elements to appropriate memory boundaries,
# .align 0 for bytes, .align 1 for half-words (shorts), .align 2 for words (ints) and
# .align 3 for doubles (longs, and if you’re a sadist, double precision floating)

		# The labels and sizes of these buffers were chosen arbitrarily. The size
		# of space could be any size that fits in memory, and the name of the label
		# can be whatever combination of words and letters you choose followed
		# immediately by a semi-colon

		# call this array A	
		.align 0		# byte-align the 40 byte space declared next
buffer1:	.space 40		# allocate 40 bytes as a read buffer for string input


		# call this array B
		.align 0	# byte-align the 96 byte array declared next
buffer2:	.space 96	# allocate 96 bytes as a read buffer for string input


		# call this array C
		.align 2	# word-align the 16 byte array declared next
buffer3:	.space 12	# make room for 3 ints (3 words)

		# declare null terminated ascii strings at address labels
		# directly in .data so we can print it later using syscalls
prompt:	.asciiz "Enter up to 39 characters to re-print: "
				
description:	.asciiz "We wrote these vowels straight to the buffer in MIPS: "

prompt2:	.asciiz "Enter 3 integers to be printed one after the next: "

space:		.asciiz " "

.text

		# This is how you read from standard in to a character array

		# This is a syscall that writes 39 characters (+ null char is 40) into
		# the read buffer at the address in $a0 from standard input

		# When we execute a syscall with the value 8 in $v0, it tells MARS that
		# we are reading a string from standard input. MARS expects the address
		# of the buffer to write to in $a0, and the number of bytes to read in 
		# $a1. Note that MARS will actually read 1 fewer bytes than indicated 
		# in $a1 because it automatically null terminates the string
		
		li $v0, 4			# syscall reads this reg when called, 4 means print string
		la $a0, prompt		# load the address of the string declared at .data
		syscall				# print the string declared at label prompt in .data
		
		li $v0, 8			# syscall reads this reg when called, 8 means read string
		la $a0, buffer1		# load the address of the buffer to write into from stdin
		li $a1, 40			# load the length of the buffer in bytes in $a1
		syscall


		# This is how you would write to standard out what you just read from
		# standard in

		# This is a syscall that reads the characters that are in the buffer
		# at address $a0 and prints them to standard output. We set $a0 equal
		# to the location of the label buffer1

		# When we execute a syscall with the value 4 in $v0, it tells MARS that
		# we are writing a string to standard out. MARS expects the address of
		# the buffer to read from in $a0.
		
		li $v0, 4			# syscall reads this reg when called, 4 means write string
		la $a0, buffer1		# load the address of the buffer to read from to stdout
		syscall
		
		
		# Here is an example of how to write the string 'aeiou\n' to a buffer and
		# null terminate it, then print it to standard out. Without the null 
		# termination the syscall wouldn't know when to stop reading characers from 
		# memory
		
		li $v0, 4
		la $a0, description
		syscall				# print the string declared at label description in .data
		
		la $t0, buffer2			# load the base address of array A declared above into $t0

		# Write 'a' to to A[0]
		li $t1, 97			# load 97 into reg $t1 (ascii value of 'a')
		sb $t1, 0($t0)			# store 97 into the first byte of readbuffer

		# You can also use hex immediates if you prefer

		# Write 'e' to A[1]
		li $t1, 0x65			# load 0x65 into reg $t1 (hex ascii value of 'e')
		sb $t1, 1($t0)			# load 0x65 into the second byte of readbuffer

		# Write 'i' to A[2]
		li $t1, 0x69			# load 0x69 into reg $t1 (hex ascii value of 'i')
		sb $t1, 2($t0)			# load 0x69 into the third byte of readbuffer

		# Write 'o' to A[3]
		li $t1, 0x6f			# ... 'o'
		sb $t1, 3($t0)			# ... 4th byte

		# Write 'u' to A[4]
		li $t1, 0x75			# ... 'u'
		sb $t1, 4($t0)			# ... 5th byte
		
		li $t1, 0x0A			# ... '\n'
		sb $t1, 5($t0)			# ... 6th byte

		# Write the null terminator (value 0) to the end of the string

		li $t1, 0			# load 0 into reg $t1 (ascii value of null terminator)
		sb $t1, 6($t0)			# store 'null' as the last byte of the buffer


		# This syscall prints the contents of buffer2 to stdout as detailed above
		li $v0, 4
		la $a0, buffer2
		syscall
		
		
		# This is an example of how to read 3 integers from the prompt and write them
		# to memory in the pre-allocated buffer3 using a loop
		
		li $v0, 4
		la $a0, prompt2
		syscall
		
		addi $s0, $0, 0		# i = 0
		la $t1, buffer3			# load base address of array C declared above under
						# label buffer3. This array is word-aligned, meaning
						# we can load and store whole words to it safely
		
		li $s1, 3			# stopping condition is i == 3

input_loop:	
		beq $s0, $s1, end_input	# if i == 3, stop taking input
		
		li $v0, 5			# tell MARS to read an integer from the user
		syscall				# the integer is returned in register $v0
		
		sll $t0, $s0, 2			# store i*4 to t0, call this offset
		add $t2, $t0, $t1		# t2 = Base address of c + offset, this is address of c[i]
		sw $v0, 0($t2)			# store the integer read into memory at c[i]
		
		addi $s0, $s0, 1		# i++
		j input_loop
		
end_input:	# 3 integers (4 bytes each) have now been read and stored to memory
		
		
		# This is an example of how to print 3 integers from the pre-allocated
		# buffer 3 using loops, which should now have 3 integers written into it by 
		# the previous lines of codes
		
		
		li $s0, 0			# i = 0
		la $t1, buffer3			# load base address of array C declared above under label
						# buffer 3. The array is word-aligned, meaning we can load
						# and store whole words to it safely
		
		li $s1, 3			# stopping condition is i == 3
		
output_loop:
		beq $s0, $s1, end_output
						# if i == 3, stop printing out
							
		sll $t0, $s0, 2			# store i*4 to t0, call this offset
		add $t2, $t0, $t1		# t2 = base address of c + offset, this is address of c[i]
		
		lw $a0, 0($t2)			# load the integer to print into register $a0 from c[i]
		
		li $v0, 1			# tell MARS to print an integer to standard out
		syscall				# the integer to print is in $v0
		
		li $v0, 4
		la $a0, space
		syscall				# tell MARS to print the space character at label space in .data
		
		addi $s0, $s0, 1
		j output_loop
		
end_output:
