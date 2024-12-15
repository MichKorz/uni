; Compile with: nasm -f elf zad_4.asm
; Link with (64 bit systems require elf_i386 option): ld -m elf_i386 zad_4.o -o zad_4
; Run with: ./zad_4
 
%include 'funs.asm'

SECTION .bss
sieve: resb 100000

SECTION .text
global  _start

_start:

    mov ecx, 0
    mov esi, sieve
setLoop:
    mov byte [esi + ecx], 1
    inc ecx
    cmp ecx, 100000
    jnz setLoop

    mov ecx, 1
sieveLoop:
    inc ecx
    mov eax, ecx
    cmp ecx, 50000
    jz fin
    cmp byte [esi + ecx], 0
    jz sieveLoop

mulLoop:
    add eax, ecx
    cmp eax, 100000
    jge sieveLoop
    mov byte [esi + eax], 0
    jmp mulLoop

fin:
    mov eax, 0
    mov ecx, 1

primePrintLoop:
    inc ecx
    cmp byte [sieve + ecx], 0
    jz primePrintLoop
    mov eax, ecx
    call iprintLF
    cmp ecx, 9999
    jnz primePrintLoop

    call quit
