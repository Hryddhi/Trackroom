from django.utils import timezone
from django.test import TestCase

# Create your tests here.

def insertion_sort(A, n):
    j = 2
    while j <= n:
        key = A[j]
        i = j - 1
        while i > 0 and key < A[i]:
            A[i+1] = A[i]
            i = i-1
        A[i+1] = key
    return A


def main():
    A = [8, 4, 2, 9, 2, 3, 6]
    A = insertion_sort(A, len(A))
    print(A)


if __name__ == "__main__":
    main()
