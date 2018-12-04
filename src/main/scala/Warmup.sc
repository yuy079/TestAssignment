def warmup(x: Int): Int = if (x==0) 1 else warmup(x-1) + warmup(x-1)
warmup(20)
/*
1. This function is taking O(2^n) because every calucation doubles the time complexity
2. Yes, You can do better by changing the code. Using dynamic programing technique, saves the value that has already been calculated.
  In that case, the time complexity would improve to O(n)
  Simply, in this warm up question, you can change warmup(x-1) + warmup(x-1) to 2*warmup(x-1), which makes the recursion linear.
  Also, you can just simply return 2*x, which takes O(1)
 */

def warmup_2 (x:Int) : Int = if (x==0) 1 else 2 * warmup_2(x-1)
warmup_2(20)

