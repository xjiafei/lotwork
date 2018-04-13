package com.winterframework.firefrog.game.lock.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.combinatorics.Factory;
import com.winterframework.combinatorics.Generator;
import com.winterframework.combinatorics.ICombinatoricsVector;

public class BaseConfig {
	public static final String prefix_total = "Number_";
	public static final String prefix_kuadu = "kuadu_";

	//--------------------------------------------------------------------------------------------------------------------------------------
	private static final String Number_0 = "000";
	private static final String Number_1 = "001 010 100";
	private static final String Number_2 = "002 011 020 101 110 200";
	private static final String Number_3 = "003 012 021 030 102 111 120 201 210 300";
	private static final String Number_4 = "004 013 022 031 040 103 112 121 130 202 211 220 301 310 400";
	private static final String Number_5 = "005 014 023 032 041 050 104 113 122 131 140 203 212 221 230 302 311 320 401 410 500";
	private static final String Number_6 = "006 015 024 033 042 051 060 105 114 123 132 141 150 204 213 222 231 240 303 312 321 330 402 411 420 501 510 600";
	private static final String Number_7 = "007 016 025 034 043 052 061 070 106 115 124 133 142 151 160 205 214 223 232 241 250 304 313 322 331 340 403 412 421 430 502 511 520 601 610 700";
	private static final String Number_8 = "008 017 026 035 044 053 062 071 080 107 116 125 134 143 152 161 170 206 215 224 233 242 251 260 305 314 323 332 341 350 404 413 422 431 440 503 512 521 530 602 611 620 701 710 800";
	private static final String Number_9 = "009 018 027 036 045 054 063 072 081 090 108 117 126 135 144 153 162 171 180 207 216 225 234 243 252 261 270 306 315 324 333 342 351 360 405 414 423 432 441 450 504 513 522 531 540 603 612 621 630 702 711 720 801 810 900";
	private static final String Number_10 = "019 028 037 046 055 064 073 082 091 109 118 127 136 145 154 163 172 181 190 208 217 226 235 244 253 262 271 280 307 316 325 334 343 352 361 370 406 415 424 433 442 451 460 505 514 523 532 541 550 604 613 622 631 640 703 712 721 730 802 811 820 901 910";
	private static final String Number_11 = "029 038 047 056 065 074 083 092 119 128 137 146 155 164 173 182 191 209 218 227 236 245 254 263 272 281 290 308 317 326 335 344 353 362 371 380 407 416 425 434 443 452 461 470 506 515 524 533 542 551 560 605 614 623 632 641 650 704 713 722 731 740 803 812 821 830 902 911 920";
	private static final String Number_12 = "039 048 057 066 075 084 093 129 138 147 156 165 174 183 192 219 228 237 246 255 264 273 282 291 309 318 327 336 345 354 363 372 381 390 408 417 426 435 444 453 462 471 480 507 516 525 534 543 552 561 570 606 615 624 633 642 651 660 705 714 723 732 741 750 804 813 822 831 840 903 912 921 930";
	private static final String Number_13 = "049 058 067 076 085 094 139 148 157 166 175 184 193 229 238 247 256 265 274 283 292 319 328 337 346 355 364 373 382 391 409 418 427 436 445 454 463 472 481 490 508 517 526 535 544 553 562 571 580 607 616 625 634 643 652 661 670 706 715 724 733 742 751 760 805 814 823 832 841 850 904 913 922 931 940";
	private static final String Number_14 = "059 068 077 086 095 149 158 167 176 185 194 239 248 257 266 275 284 293 329 338 347 356 365 374 383 392 419 428 437 446 455 464 473 482 491 509 518 527 536 545 554 563 572 581 590 608 617 626 635 644 653 662 671 680 707 716 725 734 743 752 761 770 806 815 824 833 842 851 860 905 914 923 932 941 950";
	private static final String Number_15 = "069 078 087 096 159 168 177 186 195 249 258 267 276 285 294 339 348 357 366 375 384 393 429 438 447 456 465 474 483 492 519 528 537 546 555 564 573 582 591 609 618 627 636 645 654 663 672 681 690 708 717 726 735 744 753 762 771 780 807 816 825 834 843 852 861 870 906 915 924 933 942 951 960";
	private static final String Number_16 = "079 088 097 169 178 187 196 259 268 277 286 295 349 358 367 376 385 394 439 448 457 466 475 484 493 529 538 547 556 565 574 583 592 619 628 637 646 655 664 673 682 691 709 718 727 736 745 754 763 772 781 790 808 817 826 835 844 853 862 871 880 907 916 925 934 943 952 961 970";
	private static final String Number_17 = "089 098 179 188 197 269 278 287 296 359 368 377 386 395 449 458 467 476 485 494 539 548 557 566 575 584 593 629 638 647 656 665 674 683 692 719 728 737 746 755 764 773 782 791 809 818 827 836 845 854 863 872 881 890 908 917 926 935 944 953 962 971 980";
	private static final String Number_18 = "099 189 198 279 288 297 369 378 387 396 459 468 477 486 495 549 558 567 576 585 594 639 648 657 666 675 684 693 729 738 747 756 765 774 783 792 819 828 837 846 855 864 873 882 891 909 918 927 936 945 954 963 972 981 990";
	private static final String Number_19 = "199 289 298 379 388 397 469 478 487 496 559 568 577 586 595 649 658 667 676 685 694 739 748 757 766 775 784 793 829 838 847 856 865 874 883 892 919 928 937 946 955 964 973 982 991";
	private static final String Number_20 = "299 389 398 479 488 497 569 578 587 596 659 668 677 686 695 749 758 767 776 785 794 839 848 857 866 875 884 893 929 938 947 956 965 974 983 992";
	private static final String Number_21 = "399 489 498 579 588 597 669 678 687 696 759 768 777 786 795 849 858 867 876 885 894 939 948 957 966 975 984 993";
	private static final String Number_22 = "499 589 598 679 688 697 769 778 787 796 859 868 877 886 895 949 958 967 976 985 994";
	private static final String Number_23 = "599 689 698 779 788 797 869 878 887 896 959 968 977 986 995";
	private static final String Number_24 = "699 789 798 879 888 897 969 978 987 996";
	private static final String Number_25 = "799 889 898 979 988 997";
	private static final String Number_26 = "899 989 998";
	private static final String Number_27 = "999";

	private static final String Number_z3_0 = "";
	private static final String Number_z3_1 = "001";
	private static final String Number_z3_2 = "002 110";
	private static final String Number_z3_3 = "003";
	private static final String Number_z3_4 = "004 112 220";
	private static final String Number_z3_5 = "005 113 221";
	private static final String Number_z3_6 = "006 114 330";
	private static final String Number_z3_7 = "007 115 223 331";
	private static final String Number_z3_8 = "008 116 224 332 440";
	private static final String Number_z3_9 = "009 117 225 441";
	private static final String Number_z3_10 = "118 226 334 442 550";
	private static final String Number_z3_11 = "119 227 335 443 551";
	private static final String Number_z3_12 = "228 336 552 660";
	private static final String Number_z3_13 = "229 337 445 553 661";
	private static final String Number_z3_14 = "338 446 554 662 770";
	private static final String Number_z3_15 = "339 447 663 771";
	private static final String Number_z3_16 = "448 556 664 772 880";
	private static final String Number_z3_17 = "449 557 665 773 881";
	private static final String Number_z3_18 = "558 774 882 990";
	private static final String Number_z3_19 = "559 667 775 883 991";
	private static final String Number_z3_20 = "668 776 884 992";
	private static final String Number_z3_21 = "669 885 993";
	private static final String Number_z3_22 = "778 886 994";
	private static final String Number_z3_23 = "779 887 995";
	private static final String Number_z3_24 = "996";
	private static final String Number_z3_25 = "889 997";
	private static final String Number_z3_26 = "998";
	private static final String Number_z3_27 = "";

	private static final String Number_z6_0 = "";
	private static final String Number_z6_1 = "";
	private static final String Number_z6_2 = "";
	private static final String Number_z6_3 = "012";
	private static final String Number_z6_4 = "013";
	private static final String Number_z6_5 = "014 023";
	private static final String Number_z6_6 = "015 024 123";
	private static final String Number_z6_7 = "016 025 034 124";
	private static final String Number_z6_8 = "017 026 035 125 134";
	private static final String Number_z6_9 = "018 027 036 045 126 135 234";
	private static final String Number_z6_10 = "019 028 037 046 127 136 145 235";
	private static final String Number_z6_11 = "029 038 047 056 128 137 146 236 245";
	private static final String Number_z6_12 = "039 048 057 129 138 147 156 237 246 345";
	private static final String Number_z6_13 = "049 058 067 139 148 157 238 247 256 346";
	private static final String Number_z6_14 = "059 068 149 158 167 239 248 257 347 356";
	private static final String Number_z6_15 = "069 078 159 168 249 258 267 348 357 456";
	private static final String Number_z6_16 = "079 169 178 259 268 349 358 367 457";
	private static final String Number_z6_17 = "089 179 269 278 359 368 458 467";
	private static final String Number_z6_18 = "189 279 369 378 459 468 567";
	private static final String Number_z6_19 = "289 379 469 478 568";
	private static final String Number_z6_20 = "389 479 569 578";
	private static final String Number_z6_21 = "489 579 678";
	private static final String Number_z6_22 = "589 679";
	private static final String Number_z6_23 = "689";
	private static final String Number_z6_24 = "789";
	private static final String Number_z6_25 = "";
	private static final String Number_z6_26 = "";
	private static final String Number_z6_27 = "";

	//--------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------------------------
	private static final String kuadu_z3_0 = "";
	private static final String kuadu_z3_1 = "001 011 112 122 223 233 334 344 445 455 556 566 667 677 778 788 889 899";
	private static final String kuadu_z3_2 = "002 022 113 133 224 244 335 355 446 466 557 577 668 688 779 799";
	private static final String kuadu_z3_3 = "003 033 114 144 225 255 336 366 447 477 558 588 669 699";
	private static final String kuadu_z3_4 = "004 044 115 155 226 266 337 377 448 488 559 599";
	private static final String kuadu_z3_5 = "005 055 116 166 227 277 338 388 449 499";
	private static final String kuadu_z3_6 = "006 066 117 177 228 288 339 399";
	private static final String kuadu_z3_7 = "007 077 118 188 229 299";
	private static final String kuadu_z3_8 = "008 088 119 199";
	private static final String kuadu_z3_9 = "009 099";

	private static final String kuadu_z6_0 = "";
	private static final String kuadu_z6_1 = "";
	private static final String kuadu_z6_2 = "012 123 234 345 456 567 678 789";
	private static final String kuadu_z6_3 = "013 023 124 134 235 245 346 356 457 467 568 578 679 689";
	private static final String kuadu_z6_4 = "014 024 034 125 135 145 236 246 256 347 357 367 458 468 478 569 579 589";
	private static final String kuadu_z6_5 = "015 025 035 045 126 136 146 156 237 247 257 267 348 358 368 378 459 469 479 489";
	private static final String kuadu_z6_6 = "016 026 036 046 056 127 137 147 157 167 238 248 258 268 278 349 359 369 379 389";
	private static final String kuadu_z6_7 = "017 027 037 047 057 067 128 138 148 158 168 178 239 249 259 269 279 289";
	private static final String kuadu_z6_8 = "018 028 038 048 058 068 078 129 139 149 159 169 179 189";
	private static final String kuadu_z6_9 = "019 029 039 049 059 069 079 089";

	private static final String kuadu_0 = "000 111 222 333 444 555 666 777 888 999";
	private static final String kuadu_1 = "001 010 011 100 101 110 112 121 122 211 212 221 223 232 233 322 323 332 334 343 344 433 434 443 445 454 455 544 545 554 556 565 566 655 656 665 667 676 677 766 767 776 778 787 788 877 878 887 889 898 899 988 989 998";
	private static final String kuadu_2 = "002 012 020 021 022 102 113 120 123 131 132 133 200 201 202 210 213 220 224 231 234 242 243 244 311 312 313 321 324 331 335 342 345 353 354 355 422 423 424 432 435 442 446 453 456 464 465 466 533 534 535 543 546 553 557 564 567 575 576 577 644 645 646 654 657 664 668 675 678 686 687 688 755 756 757 765 768 775 779 786 789 797 798 799 866 867 868 876 879 886 897 977 978 979 987 997";
	private static final String kuadu_3 = "003 013 023 030 031 032 033 103 114 124 130 134 141 142 143 144 203 214 225 230 235 241 245 252 253 254 255 300 301 302 303 310 314 320 325 330 336 341 346 352 356 363 364 365 366 411 412 413 414 421 425 431 436 441 447 452 457 463 467 474 475 476 477 522 523 524 525 532 536 542 547 552 558 563 568 574 578 585 586 587 588 633 634 635 636 643 647 653 658 663 669 674 679 685 689 696 697 698 699 744 745 746 747 754 758 764 769 774 785 796 855 856 857 858 865 869 875 885 896 966 967 968 969 976 986 996";
	private static final String kuadu_4 = "004 014 024 034 040 041 042 043 044 104 115 125 135 140 145 151 152 153 154 155 204 215 226 236 240 246 251 256 262 263 264 265 266 304 315 326 337 340 347 351 357 362 367 373 374 375 376 377 400 401 402 403 404 410 415 420 426 430 437 440 448 451 458 462 468 473 478 484 485 486 487 488 511 512 513 514 515 521 526 531 537 541 548 551 559 562 569 573 579 584 589 595 596 597 598 599 622 623 624 625 626 632 637 642 648 652 659 662 673 684 695 733 734 735 736 737 743 748 753 759 763 773 784 795 844 845 846 847 848 854 859 864 874 884 895 955 956 957 958 959 965 975 985 995";
	private static final String kuadu_5 = "005 015 025 035 045 050 051 052 053 054 055 105 116 126 136 146 150 156 161 162 163 164 165 166 205 216 227 237 247 250 257 261 267 272 273 274 275 276 277 305 316 327 338 348 350 358 361 368 372 378 383 384 385 386 387 388 405 416 427 438 449 450 459 461 469 472 479 483 489 494 495 496 497 498 499 500 501 502 503 504 505 510 516 520 527 530 538 540 549 550 561 572 583 594 611 612 613 614 615 616 621 627 631 638 641 649 651 661 672 683 694 722 723 724 725 726 727 732 738 742 749 752 762 772 783 794 833 834 835 836 837 838 843 849 853 863 873 883 894 944 945 946 947 948 949 954 964 974 984 994";
	private static final String kuadu_6 = "006 016 026 036 046 056 060 061 062 063 064 065 066 106 117 127 137 147 157 160 167 171 172 173 174 175 176 177 206 217 228 238 248 258 260 268 271 278 282 283 284 285 286 287 288 306 317 328 339 349 359 360 369 371 379 382 389 393 394 395 396 397 398 399 406 417 428 439 460 471 482 493 506 517 528 539 560 571 582 593 600 601 602 603 604 605 606 610 617 620 628 630 639 640 650 660 671 682 693 711 712 713 714 715 716 717 721 728 731 739 741 751 761 771 782 793 822 823 824 825 826 827 828 832 839 842 852 862 872 882 893 933 934 935 936 937 938 939 943 953 963 973 983 993";
	private static final String kuadu_7 = "007 017 027 037 047 057 067 070 071 072 073 074 075 076 077 107 118 128 138 148 158 168 170 178 181 182 183 184 185 186 187 188 207 218 229 239 249 259 269 270 279 281 289 292 293 294 295 296 297 298 299 307 318 329 370 381 392 407 418 429 470 481 492 507 518 529 570 581 592 607 618 629 670 681 692 700 701 702 703 704 705 706 707 710 718 720 729 730 740 750 760 770 781 792 811 812 813 814 815 816 817 818 821 829 831 841 851 861 871 881 892 922 923 924 925 926 927 928 929 932 942 952 962 972 982 992";
	private static final String kuadu_8 = "008 018 028 038 048 058 068 078 080 081 082 083 084 085 086 087 088 108 119 129 139 149 159 169 179 180 189 191 192 193 194 195 196 197 198 199 208 219 280 291 308 319 380 391 408 419 480 491 508 519 580 591 608 619 680 691 708 719 780 791 800 801 802 803 804 805 806 807 808 810 819 820 830 840 850 860 870 880 891 911 912 913 914 915 916 917 918 919 921 931 941 951 961 971 981 991";
	private static final String kuadu_9 = "009 019 029 039 049 059 069 079 089 090 091 092 093 094 095 096 097 098 099 109 190 209 290 309 390 409 490 509 590 609 690 709 790 809 890 900 901 902 903 904 905 906 907 908 909 910 920 930 940 950 960 970 980 990";

	private static final String baodan_z3_0 = "001 010 100 011 101 110 002 020 200 022 202 220 003 030 300 033 303 330 004 040 400 044 404 440 005 050 500 055 505"
			+" 550 006 060 600 066 606 660 007 070 700 077 707 770 008 080 800 088 808 880 009 090 900 099 909 990";
	private static final String baodan_z3_1 = "110 101 011 100 010 001 112 121 211 122 212 221 113 131 311 133 313 331 114 141 411 144 414 441 115 151 511 155 515"
			+" 551 116 161 611 166 616 661 117 171 711 177 717 771 118 181 811 188 818 881 119 191 911 199 919 991";
	private static final String baodan_z3_2 = "220 202 022 200 020 002 221 212 122 211 121 112 223 232 322 233 323 332 224 242 422 244 424 442 225 252 522 255 525"
			+" 552 226 262 622 266 626 662 227 272 722 277 727 772 228 282 822 288 828 882 229 292 922 299 929 992";
	private static final String baodan_z3_3 = "330 303 033 300 030 003 331 313 133 311 131 113 332 323 233 322 232 223 334 343 433 344 434 443 335 353 533 355 535"
			+" 553 336 363 633 366 636 663 337 373 733 377 737 773 338 383 833 388 838 883 339 393 933 399 939 993";
	private static final String baodan_z3_4 = "440 404 044 400 040 004 441 414 144 411 141 114 442 424 244 422 242 224 443 434 344 433 343 334 445 454 544 455 545"
			+" 554 446 464 644 466 646 664 447 474 744 477 747 774 448 484 844 488 848 884 449 494 944 499 949 994";
	private static final String baodan_z3_5 = "550 505 055 500 050 005 551 515 155 511 151 115 552 525 255 522 252 225 553 535 355 533 353 335 554 545 455 544 454"
			+" 445 556 565 655 566 656 665 557 575 755 577 757 775 558 585 855 588 858 885 559 595 955 599 959 995";
	private static final String baodan_z3_6 = "660 606 066 600 060 006 661 616 166 611 161 116 662 626 266 622 262 226 663 636 366 633 363 336 664 646 466 644 464"
			+" 446 665 656 566 655 565 556 667 676 766 677 767 776 668 686 866 688 868 886 669 696 966 699 969 996";
	private static final String baodan_z3_7 = "770 707 077 700 070 007 771 717 177 711 171 117 772 727 277 722 272 227 773 737 377 733 373 337 774 747 477 744 474"
			+" 447 775 757 577 755 575 557 776 767 677 766 676 667 778 787 877 788 878 887 779 797 977 799 979 997";
	private static final String baodan_z3_8 = "880 808 088 800 080 008 881 818 188 811 181 118 882 828 288 822 282 228 883 838 388 833 383 338 884 848 488 844 484"
			+" 448 885 858 588 855 585 558 886 868 688 866 686 668 887 878 788 877 787 778 889 898 988 899 989 998";
	private static final String baodan_z3_9 = "990 909 099 900 090 009 991 919 199 911 191 119 992 929 299 922 292 229 993 939 399 933 393 339 994 949 499 944 494"
			+" 449 995 959 599 955 595 559 996 969 699 966 696 669 997 979 799 977 797 779 998 989 899 988 898 889";

	private static final String baodan_z6_0 = "012 021 201 210 120 102 013 031 301 310 130 103 014 041 401 410 140 104 015 051 501 510 150 105 016 061 601 610 160"
			+" 106 017 071 701 710 170 107 018 081 801 810 180 108 019 091 901 910 190 109 023 032 302 320 230 203 024 042 402 420 240"
			+" 204 025 052 502 520 250 205 026 062 602 620 260 206 027 072 702 720 270 207 028 082 802 820 280 208 029 092 902 920 290"
			+" 209 034 043 403 430 340 304 035 053 503 530 350 305 036 063 603 630 360 306 037 073 703 730 370 307 038 083 803 830 380"
			+" 308 039 093 903 930 390 309 045 054 504 540 450 405 046 064 604 640 460 406 047 074 704 740 470 407 048 084 804 840 480"
			+" 408 049 094 904 940 490 409 056 065 605 650 560 506 057 075 705 750 570 507 058 085 805 850 580 508 059 095 905 950 590"
			+" 509 067 076 706 760 670 607 068 086 806 860 680 608 069 096 906 960 690 609 078 087 807 870 780 708 079 097 907 970 790"
			+" 709 089 098 908 980 890 809";
	private static final String baodan_z6_1 = "102 120 210 201 021 012 103 130 310 301 031 013 104 140 410 401 041 014 105 150 510 501 051 015 106 160 610 601 061"
			+" 016 107 170 710 701 071 017 108 180 810 801 081 018 109 190 910 901 091 019 123 132 312 321 231 213 124 142 412 421 241"
			+" 214 125 152 512 521 251 215 126 162 612 621 261 216 127 172 712 721 271 217 128 182 812 821 281 218 129 192 912 921 291"
			+" 219 134 143 413 431 341 314 135 153 513 531 351 315 136 163 613 631 361 316 137 173 713 731 371 317 138 183 813 831 381"
			+" 318 139 193 913 931 391 319 145 154 514 541 451 415 146 164 614 641 461 416 147 174 714 741 471 417 148 184 814 841 481"
			+" 418 149 194 914 941 491 419 156 165 615 651 561 516 157 175 715 751 571 517 158 185 815 851 581 518 159 195 915 951 591"
			+" 519 167 176 716 761 671 617 168 186 816 861 681 618 169 196 916 961 691 619 178 187 817 871 781 718 179 197 917 971 791"
			+" 719 189 198 918 981 891 819";
	private static final String baodan_z6_2 = "201 210 120 102 012 021 203 230 320 302 032 023 204 240 420 402 042 024 205 250 520 502 052 025 206 260 620 602 062"
			+" 026 207 270 720 702 072 027 208 280 820 802 082 028 209 290 920 902 092 029 213 231 321 312 132 123 214 241 421 412 142"
			+" 124 215 251 521 512 152 125 216 261 621 612 162 126 217 271 721 712 172 127 218 281 821 812 182 128 219 291 921 912 192"
			+" 129 234 243 423 432 342 324 235 253 523 532 352 325 236 263 623 632 362 326 237 273 723 732 372 327 238 283 823 832 382"
			+" 328 239 293 923 932 392 329 245 254 524 542 452 425 246 264 624 642 462 426 247 274 724 742 472 427 248 284 824 842 482"
			+" 428 249 294 924 942 492 429 256 265 625 652 562 526 257 275 725 752 572 527 258 285 825 852 582 528 259 295 925 952 592"
			+" 529 267 276 726 762 672 627 268 286 826 862 682 628 269 296 926 962 692 629 278 287 827 872 782 728 279 297 927 972 792"
			+" 729 289 298 928 982 892 829";
	private static final String baodan_z6_3 = "301 310 130 103 013 031 302 320 230 203 023 032 304 340 430 403 043 034 305 350 530 503 053 035 306 360 630 603 063"
			+" 036 307 370 730 703 073 037 308 380 830 803 083 038 309 390 930 903 093 039 312 321 231 213 123 132 314 341 431 413 143"
			+" 134 315 351 531 513 153 135 316 361 631 613 163 136 317 371 731 713 173 137 318 381 831 813 183 138 319 391 931 913 193"
			+" 139 324 342 432 423 243 234 325 352 532 523 253 235 326 362 632 623 263 236 327 372 732 723 273 237 328 382 832 823 283"
			+" 238 329 392 932 923 293 239 345 354 534 543 453 435 346 364 634 643 463 436 347 374 734 743 473 437 348 384 834 843 483"
			+" 438 349 394 934 943 493 439 356 365 635 653 563 536 357 375 735 753 573 537 358 385 835 853 583 538 359 395 935 953 593"
			+" 539 367 376 736 763 673 637 368 386 836 863 683 638 369 396 936 963 693 639 378 387 837 873 783 738 379 397 937 973 793"
			+" 739 389 398 938 983 893 839";
	private static final String baodan_z6_4 = "401 410 140 104 014 041 402 420 240 204 024 042 403 430 340 304 034 043 405 450 540 504 054 045 406 460 640 604 064"
			+" 046 407 470 740 704 074 047 408 480 840 804 084 048 409 490 940 904 094 049 412 421 241 214 124 142 413 431 341 314 134"
			+" 143 415 451 541 514 154 145 416 461 641 614 164 146 417 471 741 714 174 147 418 481 841 814 184 148 419 491 941 914 194"
			+" 149 423 432 342 324 234 243 425 452 542 524 254 245 426 462 642 624 264 246 427 472 742 724 274 247 428 482 842 824 284"
			+" 248 429 492 942 924 294 249 435 453 543 534 354 345 436 463 643 634 364 346 437 473 743 734 374 347 438 483 843 834 384"
			+" 348 439 493 943 934 394 349 456 465 645 654 564 546 457 475 745 754 574 547 458 485 845 854 584 548 459 495 945 954 594"
			+" 549 467 476 746 764 674 647 468 486 846 864 684 648 469 496 946 964 694 649 478 487 847 874 784 748 479 497 947 974 794"
			+" 749 489 498 948 984 894 849";
	private static final String baodan_z6_5 = "501 510 150 105 015 051 502 520 250 205 025 052 503 530 350 305 035 053 504 540 450 405 045 054 506 560 650 605 065"
			+" 056 507 570 750 705 075 057 508 580 850 805 085 058 509 590 950 905 095 059 512 521 251 215 125 152 513 531 351 315 135"
			+" 153 514 541 451 415 145 154 516 561 651 615 165 156 517 571 751 715 175 157 518 581 851 815 185 158 519 591 951 915 195"
			+" 159 523 532 352 325 235 253 524 542 452 425 245 254 526 562 652 625 265 256 527 572 752 725 275 257 528 582 852 825 285"
			+" 258 529 592 952 925 295 259 534 543 453 435 345 354 536 563 653 635 365 356 537 573 753 735 375 357 538 583 853 835 385"
			+" 358 539 593 953 935 395 359 546 564 654 645 465 456 547 574 754 745 475 457 548 584 854 845 485 458 549 594 954 945 495"
			+" 459 567 576 756 765 675 657 568 586 856 865 685 658 569 596 956 965 695 659 578 587 857 875 785 758 579 597 957 975 795"
			+" 759 589 598 958 985 895 859";
	private static final String baodan_z6_6 = "601 610 160 106 016 061 602 620 260 206 026 062 603 630 360 306 036 063 604 640 460 406 046 064 605 650 560 506 056"
			+" 065 607 670 760 706 076 067 608 680 860 806 086 068 609 690 960 906 096 069 612 621 261 216 126 162 613 631 361 316 136"
			+" 163 614 641 461 416 146 164 615 651 561 516 156 165 617 671 761 716 176 167 618 681 861 816 186 168 619 691 961 916 196"
			+" 169 623 632 362 326 236 263 624 642 462 426 246 264 625 652 562 526 256 265 627 672 762 726 276 267 628 682 862 826 286"
			+" 268 629 692 962 926 296 269 634 643 463 436 346 364 635 653 563 536 356 365 637 673 763 736 376 367 638 683 863 836 386"
			+" 368 639 693 963 936 396 369 645 654 564 546 456 465 647 674 764 746 476 467 648 684 864 846 486 468 649 694 964 946 496"
			+" 469 657 675 765 756 576 567 658 685 865 856 586 568 659 695 965 956 596 569 678 687 867 876 786 768 679 697 967 976 796"
			+" 769 689 698 968 986 896 869";
	private static final String baodan_z6_7 = "701 710 170 107 017 071 702 720 270 207 027 072 703 730 370 307 037 073 704 740 470 407 047 074 705 750 570 507 057"
			+" 075 706 760 670 607 067 076 708 780 870 807 087 078 709 790 970 907 097 079 712 721 271 217 127 172 713 731 371 317 137"
			+" 173 714 741 471 417 147 174 715 751 571 517 157 175 716 761 671 617 167 176 718 781 871 817 187 178 719 791 971 917 197"
			+" 179 723 732 372 327 237 273 724 742 472 427 247 274 725 752 572 527 257 275 726 762 672 627 267 276 728 782 872 827 287"
			+" 278 729 792 972 927 297 279 734 743 473 437 347 374 735 753 573 537 357 375 736 763 673 637 367 376 738 783 873 837 387"
			+" 378 739 793 973 937 397 379 745 754 574 547 457 475 746 764 674 647 467 476 748 784 874 847 487 478 749 794 974 947 497"
			+" 479 756 765 675 657 567 576 758 785 875 857 587 578 759 795 975 957 597 579 768 786 876 867 687 678 769 796 976 967 697"
			+" 679 789 798 978 987 897 879";
	private static final String baodan_z6_8 = "801 810 180 108 018 081 802 820 280 208 028 082 803 830 380 308 038 083 804 840 480 408 048 084 805 850 580 508 058"
			+" 085 806 860 680 608 068 086 807 870 780 708 078 087 809 890 980 908 098 089 812 821 281 218 128 182 813 831 381 318 138"
			+" 183 814 841 481 418 148 184 815 851 581 518 158 185 816 861 681 618 168 186 817 871 781 718 178 187 819 891 981 918 198"
			+" 189 823 832 382 328 238 283 824 842 482 428 248 284 825 852 582 528 258 285 826 862 682 628 268 286 827 872 782 728 278"
			+" 287 829 892 982 928 298 289 834 843 483 438 348 384 835 853 583 538 358 385 836 863 683 638 368 386 837 873 783 738 378"
			+" 387 839 893 983 938 398 389 845 854 584 548 458 485 846 864 684 648 468 486 847 874 784 748 478 487 849 894 984 948 498"
			+" 489 856 865 685 658 568 586 857 875 785 758 578 587 859 895 985 958 598 589 867 876 786 768 678 687 869 896 986 968 698"
			+" 689 879 897 987 978 798 789";
	private static final String baodan_z6_9 = "901 910 190 109 019 091 902 920 290 209 029 092 903 930 390 309 039 093 904 940 490 409 049 094 905 950 590 509 059"
			+" 095 906 960 690 609 069 096 907 970 790 709 079 097 908 980 890 809 089 098 912 921 291 219 129 192 913 931 391 319 139"
			+" 193 914 941 491 419 149 194 915 951 591 519 159 195 916 961 691 619 169 196 917 971 791 719 179 197 918 981 891 819 189"
			+" 198 923 932 392 329 239 293 924 942 492 429 249 294 925 952 592 529 259 295 926 962 692 629 269 296 927 972 792 729 279"
			+" 297 928 982 892 829 289 298 934 943 493 439 349 394 935 953 593 539 359 395 936 963 693 639 369 396 937 973 793 739 379"
			+" 397 938 983 893 839 389 398 945 954 594 549 459 495 946 964 694 649 469 496 947 974 794 749 479 497 948 984 894 849 489"
			+" 498 956 965 695 659 569 596 957 975 795 759 579 597 958 985 895 859 589 598 967 976 796 769 679 697 968 986 896 869 689"
			+" 698 978 987 897 879 789 798";

	
	private static final String X2_Hz_0="00";
	private static final String X2_Hz_1="01 10";
	private static final String X2_Hz_2="02 11 20";
	private static final String X2_Hz_3="03 12 21 30";
	private static final String X2_Hz_4="04 13 22 31 40";
	private static final String X2_Hz_5="05 14 23 32 41 50";
	private static final String X2_Hz_6="06 15 24 33 42 51 60";
	private static final String X2_Hz_7="07 16 25 34 43 52 61 70";
	private static final String X2_Hz_8="08 17 26 35 44 53 62 71 80";
	private static final String X2_Hz_9="09 18 27 36 45 54 63 72 81 90";
	private static final String X2_Hz_10="19 28 37 46 55 64 73 82 91";
	private static final String X2_Hz_11="29 38 47 56 65 74 83 92";
	private static final String X2_Hz_12="39 48 57 66 75 84 93";
	private static final String X2_Hz_13="49 58 67 76 85 94";
	private static final String X2_Hz_14="59 68 77 86 95";
	private static final String X2_Hz_15="69 78 87 96";
	private static final String X2_Hz_16="79 88 97";
	private static final String X2_Hz_17="89 98";
	private static final String X2_Hz_18="99";
	
	
	private static final String X2_Kd_0="00 11 22 33 44 55 66 77 88 99";
	private static final String X2_Kd_1="01 10 12 21 23 32 34 43 45 54 56 65 67 76 78 87 89 98";
	private static final String X2_Kd_2="02 13 20 24 31 35 42 46 53 57 64 68 75 79 86 97";
	private static final String X2_Kd_3="03 14 25 30 36 41 47 52 58 63 69 74 85 96";
	private static final String X2_Kd_4="04 15 26 37 40 48 51 59 62 73 84 95";
	private static final String X2_Kd_5="05 16 27 38 49 50 61 72 83 94";
	private static final String X2_Kd_6="06 17 28 39 60 71 82 93";
	private static final String X2_Kd_7="07 18 29 70 81 92";
	private static final String X2_Kd_8="08 19 80 91";
	private static final String X2_Kd_9="09 90";
	
	private static final String X2_zxHZ_0="";
	private static final String X2_zxHZ_1="10 01";
	private static final String X2_zxHZ_2="20 02";
	private static final String X2_zxHZ_3="21 30 12 03";
	private static final String X2_zxHZ_4="31 40 13 04";
	private static final String X2_zxHZ_5="32 41 50 23 14 05";
	private static final String X2_zxHZ_6="42 51 60 24 15 06";
	private static final String X2_zxHZ_7="43 52 61 70 34 25 16 07";
	private static final String X2_zxHZ_8="53 62 71 80 35 26 17 08";
	private static final String X2_zxHZ_9="54 63 72 81 90 45 36 27 18 09";
	private static final String X2_zxHZ_10="64 73 82 91 46 37 28 19";
	private static final String X2_zxHZ_11="65 74 83 92 56 47 38 29";
	private static final String X2_zxHZ_12="75 84 93 57 48 39";
	private static final String X2_zxHZ_13="76 85 94 67 58 49";
	private static final String X2_zxHZ_14="86 95 68 59";
	private static final String X2_zxHZ_15="87 96 78 69";
	private static final String X2_zxHZ_16="97 79";
	private static final String X2_zxHZ_17="98 89";
	private static final String X2_zxHZ_18="";
	private static final Map<String, String[]> keyMap = new HashMap<String, String[]>();

	static {
		keyMap.put("Number_0", Number_0.split(" "));
		keyMap.put("Number_1", Number_1.split(" "));
		keyMap.put("Number_2", Number_2.split(" "));
		keyMap.put("Number_3", Number_3.split(" "));
		keyMap.put("Number_4", Number_4.split(" "));
		keyMap.put("Number_5", Number_5.split(" "));
		keyMap.put("Number_6", Number_6.split(" "));
		keyMap.put("Number_7", Number_7.split(" "));
		keyMap.put("Number_8", Number_8.split(" "));
		keyMap.put("Number_9", Number_9.split(" "));
		keyMap.put("Number_10", Number_10.split(" "));
		keyMap.put("Number_11", Number_11.split(" "));
		keyMap.put("Number_12", Number_12.split(" "));
		keyMap.put("Number_13", Number_13.split(" "));
		keyMap.put("Number_14", Number_14.split(" "));
		keyMap.put("Number_15", Number_15.split(" "));
		keyMap.put("Number_16", Number_16.split(" "));
		keyMap.put("Number_17", Number_17.split(" "));
		keyMap.put("Number_18", Number_18.split(" "));
		keyMap.put("Number_19", Number_19.split(" "));
		keyMap.put("Number_20", Number_20.split(" "));
		keyMap.put("Number_21", Number_21.split(" "));
		keyMap.put("Number_22", Number_22.split(" "));
		keyMap.put("Number_23", Number_23.split(" "));
		keyMap.put("Number_24", Number_24.split(" "));
		keyMap.put("Number_25", Number_25.split(" "));
		keyMap.put("Number_26", Number_26.split(" "));
		keyMap.put("Number_27", Number_27.split(" "));

		keyMap.put("Number_z3_0", Number_z3_0.split(" "));
		keyMap.put("Number_z3_1", Number_z3_1.split(" "));
		keyMap.put("Number_z3_2", Number_z3_2.split(" "));
		keyMap.put("Number_z3_3", Number_z3_3.split(" "));
		keyMap.put("Number_z3_4", Number_z3_4.split(" "));
		keyMap.put("Number_z3_5", Number_z3_5.split(" "));
		keyMap.put("Number_z3_6", Number_z3_6.split(" "));
		keyMap.put("Number_z3_7", Number_z3_7.split(" "));
		keyMap.put("Number_z3_8", Number_z3_8.split(" "));
		keyMap.put("Number_z3_9", Number_z3_9.split(" "));
		keyMap.put("Number_z3_10", Number_z3_10.split(" "));
		keyMap.put("Number_z3_11", Number_z3_11.split(" "));
		keyMap.put("Number_z3_12", Number_z3_12.split(" "));
		keyMap.put("Number_z3_13", Number_z3_13.split(" "));
		keyMap.put("Number_z3_14", Number_z3_14.split(" "));
		keyMap.put("Number_z3_15", Number_z3_15.split(" "));
		keyMap.put("Number_z3_16", Number_z3_16.split(" "));
		keyMap.put("Number_z3_17", Number_z3_17.split(" "));
		keyMap.put("Number_z3_18", Number_z3_18.split(" "));
		keyMap.put("Number_z3_19", Number_z3_19.split(" "));
		keyMap.put("Number_z3_20", Number_z3_20.split(" "));
		keyMap.put("Number_z3_21", Number_z3_21.split(" "));
		keyMap.put("Number_z3_22", Number_z3_22.split(" "));
		keyMap.put("Number_z3_23", Number_z3_23.split(" "));
		keyMap.put("Number_z3_24", Number_z3_24.split(" "));
		keyMap.put("Number_z3_25", Number_z3_25.split(" "));
		keyMap.put("Number_z3_26", Number_z3_26.split(" "));
		keyMap.put("Number_z3_27", Number_z3_27.split(" "));

		keyMap.put("Number_z6_0", Number_z6_0.split(" "));
		keyMap.put("Number_z6_1", Number_z6_1.split(" "));
		keyMap.put("Number_z6_2", Number_z6_2.split(" "));
		keyMap.put("Number_z6_3", Number_z6_3.split(" "));
		keyMap.put("Number_z6_4", Number_z6_4.split(" "));
		keyMap.put("Number_z6_5", Number_z6_5.split(" "));
		keyMap.put("Number_z6_6", Number_z6_6.split(" "));
		keyMap.put("Number_z6_7", Number_z6_7.split(" "));
		keyMap.put("Number_z6_8", Number_z6_8.split(" "));
		keyMap.put("Number_z6_9", Number_z6_9.split(" "));
		keyMap.put("Number_z6_10", Number_z6_10.split(" "));
		keyMap.put("Number_z6_11", Number_z6_11.split(" "));
		keyMap.put("Number_z6_12", Number_z6_12.split(" "));
		keyMap.put("Number_z6_13", Number_z6_13.split(" "));
		keyMap.put("Number_z6_14", Number_z6_14.split(" "));
		keyMap.put("Number_z6_15", Number_z6_15.split(" "));
		keyMap.put("Number_z6_16", Number_z6_16.split(" "));
		keyMap.put("Number_z6_17", Number_z6_17.split(" "));
		keyMap.put("Number_z6_18", Number_z6_18.split(" "));
		keyMap.put("Number_z6_19", Number_z6_19.split(" "));
		keyMap.put("Number_z6_20", Number_z6_20.split(" "));
		keyMap.put("Number_z6_21", Number_z6_21.split(" "));
		keyMap.put("Number_z6_22", Number_z6_22.split(" "));
		keyMap.put("Number_z6_23", Number_z6_23.split(" "));
		keyMap.put("Number_z6_24", Number_z6_24.split(" "));
		keyMap.put("Number_z6_25", Number_z6_25.split(" "));
		keyMap.put("Number_z6_26", Number_z6_26.split(" "));
		keyMap.put("Number_z6_27", Number_z6_27.split(" "));

		keyMap.put("kuadu_0", kuadu_0.split(" "));
		keyMap.put("kuadu_1", kuadu_1.split(" "));
		keyMap.put("kuadu_2", kuadu_2.split(" "));
		keyMap.put("kuadu_3", kuadu_3.split(" "));
		keyMap.put("kuadu_4", kuadu_4.split(" "));
		keyMap.put("kuadu_5", kuadu_5.split(" "));
		keyMap.put("kuadu_6", kuadu_6.split(" "));
		keyMap.put("kuadu_7", kuadu_7.split(" "));
		keyMap.put("kuadu_8", kuadu_8.split(" "));
		keyMap.put("kuadu_9", kuadu_9.split(" "));

		keyMap.put("kuadu_z3_0", kuadu_z3_0.split(" "));
		keyMap.put("kuadu_z3_1", kuadu_z3_1.split(" "));
		keyMap.put("kuadu_z3_2", kuadu_z3_2.split(" "));
		keyMap.put("kuadu_z3_3", kuadu_z3_3.split(" "));
		keyMap.put("kuadu_z3_4", kuadu_z3_4.split(" "));
		keyMap.put("kuadu_z3_5", kuadu_z3_5.split(" "));
		keyMap.put("kuadu_z3_6", kuadu_z3_6.split(" "));
		keyMap.put("kuadu_z3_7", kuadu_z3_7.split(" "));
		keyMap.put("kuadu_z3_8", kuadu_z3_8.split(" "));
		keyMap.put("kuadu_z3_9", kuadu_z3_9.split(" "));

		keyMap.put("kuadu_z6_0", kuadu_z6_0.split(" "));
		keyMap.put("kuadu_z6_1", kuadu_z6_1.split(" "));
		keyMap.put("kuadu_z6_2", kuadu_z6_2.split(" "));
		keyMap.put("kuadu_z6_3", kuadu_z6_3.split(" "));
		keyMap.put("kuadu_z6_4", kuadu_z6_4.split(" "));
		keyMap.put("kuadu_z6_5", kuadu_z6_5.split(" "));
		keyMap.put("kuadu_z6_6", kuadu_z6_6.split(" "));
		keyMap.put("kuadu_z6_7", kuadu_z6_7.split(" "));
		keyMap.put("kuadu_z6_8", kuadu_z6_8.split(" "));
		keyMap.put("kuadu_z6_9", kuadu_z6_9.split(" "));

		keyMap.put("baodan_z3_0", baodan_z3_0.split(" "));
		keyMap.put("baodan_z3_1", baodan_z3_1.split(" "));
		keyMap.put("baodan_z3_2", baodan_z3_2.split(" "));
		keyMap.put("baodan_z3_3", baodan_z3_3.split(" "));
		keyMap.put("baodan_z3_4", baodan_z3_4.split(" "));
		keyMap.put("baodan_z3_5", baodan_z3_5.split(" "));
		keyMap.put("baodan_z3_6", baodan_z3_6.split(" "));
		keyMap.put("baodan_z3_7", baodan_z3_7.split(" "));
		keyMap.put("baodan_z3_8", baodan_z3_8.split(" "));
		keyMap.put("baodan_z3_9", baodan_z3_9.split(" "));

		keyMap.put("baodan_z6_0", baodan_z6_0.split(" "));
		keyMap.put("baodan_z6_1", baodan_z6_1.split(" "));
		keyMap.put("baodan_z6_2", baodan_z6_2.split(" "));
		keyMap.put("baodan_z6_3", baodan_z6_3.split(" "));
		keyMap.put("baodan_z6_4", baodan_z6_4.split(" "));
		keyMap.put("baodan_z6_5", baodan_z6_5.split(" "));
		keyMap.put("baodan_z6_6", baodan_z6_6.split(" "));
		keyMap.put("baodan_z6_7", baodan_z6_7.split(" "));
		keyMap.put("baodan_z6_8", baodan_z6_8.split(" "));
		keyMap.put("baodan_z6_9", baodan_z6_9.split(" "));
		
		
		keyMap.put("X2_Hz_0",X2_Hz_0.split(" "));
		keyMap.put("X2_Hz_1",X2_Hz_1.split(" "));
		keyMap.put("X2_Hz_2",X2_Hz_2.split(" "));
		keyMap.put("X2_Hz_3",X2_Hz_3.split(" "));
		keyMap.put("X2_Hz_4",X2_Hz_4.split(" "));
		keyMap.put("X2_Hz_5",X2_Hz_5.split(" "));
		keyMap.put("X2_Hz_6",X2_Hz_6.split(" "));
		keyMap.put("X2_Hz_7",X2_Hz_7.split(" "));
		keyMap.put("X2_Hz_8",X2_Hz_8.split(" "));
		keyMap.put("X2_Hz_9",X2_Hz_9.split(" "));
		keyMap.put("X2_Hz_10",X2_Hz_10.split(" "));
		keyMap.put("X2_Hz_11",X2_Hz_11.split(" "));
		keyMap.put("X2_Hz_12",X2_Hz_12.split(" "));
		keyMap.put("X2_Hz_13",X2_Hz_13.split(" "));
		keyMap.put("X2_Hz_14",X2_Hz_14.split(" "));
		keyMap.put("X2_Hz_15",X2_Hz_15.split(" "));
		keyMap.put("X2_Hz_16",X2_Hz_16.split(" "));
		keyMap.put("X2_Hz_17",X2_Hz_17.split(" "));
		keyMap.put("X2_Hz_18",X2_Hz_18.split(" "));
		
		keyMap.put("X2_Kd_0",X2_Kd_0.split(" "));
		keyMap.put("X2_Kd_1",X2_Kd_1.split(" "));
		keyMap.put("X2_Kd_2",X2_Kd_2.split(" "));
		keyMap.put("X2_Kd_3",X2_Kd_3.split(" "));
		keyMap.put("X2_Kd_4",X2_Kd_4.split(" "));
		keyMap.put("X2_Kd_5",X2_Kd_5.split(" "));
		keyMap.put("X2_Kd_6",X2_Kd_6.split(" "));
		keyMap.put("X2_Kd_7",X2_Kd_7.split(" "));
		keyMap.put("X2_Kd_8",X2_Kd_8.split(" "));
		keyMap.put("X2_Kd_9",X2_Kd_9.split(" "));
		
		keyMap.put("X2_zxHZ_0",X2_zxHZ_0.split(" "));
		keyMap.put("X2_zxHZ_1",X2_zxHZ_1.split(" "));
		keyMap.put("X2_zxHZ_2",X2_zxHZ_2.split(" "));
		keyMap.put("X2_zxHZ_3",X2_zxHZ_3.split(" "));
		keyMap.put("X2_zxHZ_4",X2_zxHZ_4.split(" "));
		keyMap.put("X2_zxHZ_5",X2_zxHZ_5.split(" "));
		keyMap.put("X2_zxHZ_6",X2_zxHZ_6.split(" "));
		keyMap.put("X2_zxHZ_7",X2_zxHZ_7.split(" "));
		keyMap.put("X2_zxHZ_8",X2_zxHZ_8.split(" "));
		keyMap.put("X2_zxHZ_9",X2_zxHZ_9.split(" "));
		keyMap.put("X2_zxHZ_10",X2_zxHZ_10.split(" "));
		keyMap.put("X2_zxHZ_11",X2_zxHZ_11.split(" "));
		keyMap.put("X2_zxHZ_12",X2_zxHZ_12.split(" "));
		keyMap.put("X2_zxHZ_13",X2_zxHZ_13.split(" "));
		keyMap.put("X2_zxHZ_14",X2_zxHZ_14.split(" "));
		keyMap.put("X2_zxHZ_15",X2_zxHZ_15.split(" "));
		keyMap.put("X2_zxHZ_16",X2_zxHZ_16.split(" "));
		keyMap.put("X2_zxHZ_17",X2_zxHZ_17.split(" "));
		keyMap.put("X2_zxHZ_18",X2_zxHZ_18.split(" "));

	}

	public static String[] getHezhi(String number) {
		return keyMap.get("Number_" + number);
	};

	public static String[] getZ3Hezhi(String number) {
		return keyMap.get("Number_z3_" + number);
	};

	public static String[] getZ6Hezhi(String number) {
		return keyMap.get("Number_z6_" + number);
	};

	public static String[] getKuadu(String number) {
		return keyMap.get("kuadu_" + number);
	};

	public static String[] getZ3Kuadu(String number) {
		return keyMap.get("kuadu_z3_" + number);
	};

	public static String[] getZ6Kuadu(String number) {
		return keyMap.get("kuadu_z6_" + number);
	};
	public static String[] getZ3Baodan(String number) {
		return keyMap.get("baodan_z3_" + number);
	};
	public static String[] getZ6Baodan(String number) {
		return keyMap.get("baodan_z6_" + number);
	};
	
	public static String[] getX2Hz(String number) {
		return keyMap.get("X2_Hz_" + number);
	};
	public static String[] getX2Kd(String number) {
		return keyMap.get("X2_Kd_" + number);
	};
	public static String[] getX2ZxHz(String number) {
		return keyMap.get("X2_zxHZ_" + number);
	};
	
	
	
	
	public static void createBaodan_z6(){
		
		String[] nums = {"0","1","2","3","4","5","6","7","8","9"};
		
		for (String num : nums) {

			ICombinatoricsVector<String> initialVector = Factory.createVector(LockTools.getBaseNum("0123456789".replaceAll(num, "")));
			Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector,2);
			List<String> results = new ArrayList<String>();
			String result = "";
			for (ICombinatoricsVector<String> combination : gen) {
				String[] abcd=new String[3];
				abcd[0]=num;
				abcd[1]=combination.getValue(0);
				abcd[2]=combination.getValue(1);
				ICombinatoricsVector<String> initialVector2 = Factory.createVector(abcd);
				Generator<String> gen2=Factory.createPermutationGenerator(initialVector2);
				for (ICombinatoricsVector<String> combination2 : gen2) {
					String inner = "";
					for (String string : combination2) {
						inner += string;
					}
					results.add(inner);
				}
			}
			
			for (String string : results) {
				result += " " + string;
			}
			
			System.out.println(num + " : " + result);
		}
		
		
	}
	
	public static void createBaodan_z3(){
		
		String[] nums = {"0","1","2","3","4","5","6","7","8","9"};
		
		for (String num : nums) {

			ICombinatoricsVector<String> initialVector = Factory.createVector(LockTools.getBaseNum("0123456789".replaceAll(num, "")));
			Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector,1);
			List<String> results = new ArrayList<String>();
			String result = "";
			for (ICombinatoricsVector<String> combination : gen) {
				String[] abcd=new String[3];
				abcd[0]=num;
				abcd[1]=num;
				abcd[2]=combination.getValue(0);
				ICombinatoricsVector<String> initialVector2 = Factory.createVector(abcd);
				Generator<String> gen2=Factory.createPermutationGenerator(initialVector2);
				for (ICombinatoricsVector<String> combination2 : gen2) {
					String inner = "";
					for (String string : combination2) {
						inner += string;
					}
					results.add(inner);
				}
				
				String[] abcd1=new String[3];
				abcd1[0]=num;
				abcd1[1]=combination.getValue(0);
				abcd1[2]=combination.getValue(0);
				ICombinatoricsVector<String> initialVector3 = Factory.createVector(abcd1);
				Generator<String> gen3=Factory.createPermutationGenerator(initialVector3);
				for (ICombinatoricsVector<String> combination3 : gen3) {
					String inner = "";
					for (String string : combination3) {
						inner += string;
					}
					results.add(inner);
				}
			}
			
			for (String string : results) {
				result += " " + string;
			}
			
			System.out.println(num + " : " + result);
		}
		
		
	}
	
	public static void main(String[] args){
		createBaodan_z3();
	}
}
