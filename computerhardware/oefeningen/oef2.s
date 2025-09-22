	.file	"test3.c"
	.intel_syntax noprefix
	.text
	.globl	f
	.type	f, @function
f:
	push	ebp
	mov	ebp, esp
	sub	esp, 16
	mov	DWORD PTR [ebp-4], 0
	jmp	.L2
.L4:
	mov	eax, DWORD PTR [ebp+8]
	mov	eax, DWORD PTR [eax]
	lea	edx, [eax+4]
	mov	eax, DWORD PTR [ebp+8]
	mov	DWORD PTR [eax], edx
	add	DWORD PTR [ebp-4], 1
.L2:
	mov	eax, DWORD PTR [ebp-4]
	cmp	eax, DWORD PTR [ebp+12]
	jge	.L3
	mov	eax, DWORD PTR [ebp+8]
	mov	eax, DWORD PTR [eax]
	mov	eax, DWORD PTR [eax]
	cmp	DWORD PTR [ebp+16], eax
	jne	.L4
.L3:
	mov	eax, DWORD PTR [ebp-4]
	cmp	eax, DWORD PTR [ebp+12]
	jne	.L6
	mov	eax, DWORD PTR [ebp+8]
	mov	DWORD PTR [eax], 0
.L6:
	nop
	leave
	ret
	.size	f, .-f
	.section	.rodata
.LC0:
	.string	"%d"
	.text
	.globl	main
	.type	main, @function
main:
	lea	ecx, [esp+4]
	and	esp, -16
	push	DWORD PTR [ecx-4]
	push	ebp
	mov	ebp, esp
	push	ecx
	sub	esp, 36
	mov	DWORD PTR [ebp-24], 7
	mov	DWORD PTR [ebp-20], 8
	mov	DWORD PTR [ebp-16], 15
	mov	DWORD PTR [ebp-12], 3
	lea	eax, [ebp-24]
	mov	DWORD PTR [ebp-28], eax
	push	8
	push	4
	lea	eax, [ebp-28]
	push	eax
	call	f
	add	esp, 12
	mov	eax, DWORD PTR [ebp-28]
	test	eax, eax
	je	.L8
	mov	eax, DWORD PTR [ebp-28]
	mov	eax, DWORD PTR [eax]
	sub	esp, 8
	push	eax
	push	OFFSET FLAT:.LC0
	call	printf
	add	esp, 16
	jmp	.L9
.L8:
	sub	esp, 8
	push	0
	push	OFFSET FLAT:.LC0
	call	printf
	add	esp, 16
.L9:
	mov	eax, 0
	mov	ecx, DWORD PTR [ebp-4]
	leave
	lea	esp, [ecx-4]
	ret
	.size	main, .-main
	.ident	"GCC: (GNU) 10.3.1 20210422 (Red Hat 10.3.1-1)"
	.section	.note.GNU-stack,"",@progbits
