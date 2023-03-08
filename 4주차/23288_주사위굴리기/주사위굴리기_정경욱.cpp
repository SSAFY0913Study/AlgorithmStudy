#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

long long int calNum(long long int n) {//n���� ���� �� �� ���� ū 2�� �ŵ����� ���ϱ�
	long long int num = 1;
	while (num < n) {
		num *= 2;
	}

	return num / 2;
}

int calChar(long long int num) {
	if (num == 1) return 0;	//num�� 1�϶� 0�� ��ȯ�Ѵ�.
	return calChar(num - calNum(num)) ^ 1;	//num���� num���� ���� �� �� ���� ū 2�� �ŵ������� �� ������ xor������ �Ѵ�.
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