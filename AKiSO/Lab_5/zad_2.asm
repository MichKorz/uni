; nasm -f elf -g -F dwarf zad_2.asm
; Compile with: nasm -f elf zad_2.asm
; Link with (64 bit systems require elf_i386 option): ld -m elf_i386 zad_2.o -o zad_2
; Run with: ./zad_2
 
%include 'funs.asm'

SECTION .data
matrix: db 1,2,3,4,5,6,7,8,9

SECTION .bss
sum: resb 1
diagonal: resb 1

SECTION .text
global  _start
 
_start:
    mov byte [sum], 0
    mov byte [diagonal], 0

    mov ecx, 0
matrixLoop:
    mov al, 0
    add al, byte [matrix + ecx]
    add byte [sum], al 
    inc ecx
    cmp ecx, 9
    jnz matrixLoop

    mov al, [matrix + 2]
    add [diagonal], al
    mov al, [matrix + 4]
    add [diagonal], al
    mov al, [matrix + 6]
    add [diagonal], al

    mov al, byte [sum]
    call iprintLF
    mov al, byte[diagonal]
    call iprintLF

    call quit
