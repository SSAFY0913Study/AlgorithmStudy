#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

long long int calNum(long long int n) {//n보다 작은 수 중 가장 큰 2의 거듭제곱 구하기
	long long int num = 1;
	while (num < n) {
		num *= 2;
	}

	return num / 2;
}

int calChar(long long int num) {
	if (num == 1) return 0;	//num이 1일때 0을 반환한다.
	return calChar(num - calNum(num)) ^ 1;	//num에서 num보다 작은 수 중 가장 큰 2의 거듭제곱을 뺀 값에서 xor연산을 한다.
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	long long int k;

	cin >> k;

	cout << calChar(k);

	return 0;
}