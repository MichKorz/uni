; Compile with: nasm -f elf zad_3.asm
; Link with (64 bit systems require elf_i386 option): ld -m elf_i386 zad_3.o -o zad_3
; Run with: ./zad_3
;3735928559 DEADBEEF
 
%include 'funs.asm'

SECTION .bss
sinput: resb 255

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

    mov ecx, 0
toHexLoop:
    mov edx, 0
    mov esi, 16
    idiv esi
    push edx
    inc ecx
    cmp eax, 0
    jnz toHexLoop

hexPrintLoop:
    pop eax
    call hexToString
    dec ecx
    cmp ecx, 0
    jnz hexPrintLoop

    ;mov eax, 32
    ;call sprintLF


    call quit
