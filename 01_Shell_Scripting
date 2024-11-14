// Sum of digits
#!/bin/bash

# Function to calculate the sum of digits
sum_of_digits() {
    local number=$1
    local sum=0

    while [ $number -gt 0 ]; do
        digit=$((number % 10))
        sum=$((sum + digit))
        number=$((number / 10))
    done

    echo "Sum of digits: $sum"
}

# Read input from user
read -p "Enter a number: " num
sum_of_digits $num

// Factorial
#!/bin/bash

# Function to calculate factorial
factorial() {
    local number=$1
    local fact=1

    for (( i=1; i<=number; i++ )); do
        fact=$((fact * i))
    done

    echo "Factorial of $number: $fact"
}

# Read input from user
read -p "Enter a number: " num
factorial $num

// Bubble sort
#!/bin/bash

# Function to perform bubble sort
bubble_sort() {
    local arr=("$@")
    local n=${#arr[@]}

    for (( i=0; i<n; i++ )); do
        for (( j=0; j<n-i-1; j++ )); do
            if [ ${arr[j]} -gt ${arr[j+1]} ]; then
                # Swap
                temp=${arr[j]}
                arr[j]=${arr[j+1]}
                arr[j+1]=$temp
            fi
        done
    done

    echo "Sorted array: ${arr[@]}"
}

# Read input from user
read -p "Enter numbers to sort (space-separated): " -a numbers
bubble_sort "${numbers[@]}"


#!/bin/bash

# Function to perform insertion sort
insertion_sort() {
    local arr=("$@")
    local n=${#arr[@]}

    for (( i=1; i<n; i++ )); do
        key=${arr[i]}
        j=$((i - 1))

        # Move elements of arr[0..i-1], that are greater than key,
        # to one position ahead of their current position
        while [ $j -ge 0 ] && [ ${arr[j]} -gt $key ]; do
            arr[$((j + 1))]=${arr[j]}
            j=$((j - 1))
        done
        arr[$((j + 1))]=$key
    done

    echo "Sorted array: ${arr[@]}"
}

# Read input from user
read -p "Enter numbers to sort (space-separated): " -a numbers
insertion_sort "${numbers[@]}"
