.data    # This declares data elements that will be placed in the global data region.
            # The global data region starts at address 0x10010000.  The default memory snapshot
            # shown in MARS starts at this point.
label1: .word 5  # This declares a 32-bit entity that is initialized to 5.  After assembling, make
                         # sure you see “5” in the first word of the memory region starting at 0x10010000
                         # To access this word, your program will use the label “label1”.  Notice that the
                         # assembler will convert references to “label1” with the appropriate address.
label2: .word 7 # This declares a 32-bit entity that is initialized to 7.  
label3: .asciiz  "The answer is "   # This declares a string with label “label3”.

.text     # I’m now entering the text/code region.  Note that you can go back and forth between
            # .text and .data any time.
lw  $t0, label1   # This loads the value at address “label1” into register $t0.  As you step through
                         # the program, make sure that $t0 now has the value 5.  Also observe how the
                         # assembler translates label1 into the appropriate address sequence.  In many
                         # instances, you’ll see that the assembler converts your pseudo-instructions
                         # into multiple MIPS instructions.
lw  $t1, label2   # This loads the value at address “label2” into register $t1.
add $t2, $t1, $t0  # At the end of this, $t2 should have the value 12.
li   $v0, 4          # I am getting ready to do a system call.  I use the pseudo-instruction “li”,
                        # which means load-immediate.  This puts the immediate operand “4” into
                        # register $v0.  I’m doing this to specify the “print string” system call.
la $a0, label3   # I’m using pseudo-instruction “la” which refers to load-address.  I’m loading
                        # the address of the string “The answer is: ” into register $a0.  This will serve
                        # as the argument to the upcoming system call.  Note that I referred to the
                        # string by its label.
syscall             # This invokes the system call.  The system call examines $v0 and $a0 and
                        # goes on to print the string.
li   $v0, 1         # I’m now going to do a system call to print an integer.
move $a0, $t2  # I’m using pseudo-instruction move to copy the answer in $t2 into the
                         # argument register $a0 for the system call.
syscall              # Print the integer answer.
