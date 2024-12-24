; Compile with: nasm -f elf zad_1.asm
; Link with (64 bit systems require elf_i386 option): ld -m elf_i386 zad_1.o -o zad_1
; Run with: ./zad_1
 
%include 'funs.asm'

SECTION .bss
sinput: resb 255
result resb 4

SECTION .text
global  _start
 
_start:
 
    mov edx, 255
    mov ecx, sinput
    mov ebx, 0
    mov eax, 3
    int 80h

    mov eax, sinput
    call atoi

sumLoop:
    mov edx, 0
    mov esi, 10
    idiv esi
    add [result], edx
    cmp eax, 0
    jnz sumLoop

    mov eax, [result]
    call iprintLF
 
    call quit
