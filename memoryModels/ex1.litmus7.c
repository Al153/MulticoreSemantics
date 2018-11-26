X86 ex
{
	x=0; y=0;
}

P0			| P1 		 	| P2			;
MOV [x],$1  | MOV EBX, [x]	| MOV [y], $1 	;
			| MOV ECX, [y] 	| MOV EAX, [x]	;
exists 
(
	1:EBX=1 /\ 1:ECX=0 /\ 2:EAX=0
)