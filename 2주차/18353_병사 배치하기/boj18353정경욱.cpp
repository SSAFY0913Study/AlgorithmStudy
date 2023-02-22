#include <iostream>
#include <algorithm>
#include <vector>
#include <limits.h>
using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int N, maxLen = 0;

	cin >> N;

	vector<int> arr(N + 1); //������ �������� ����� �迭
	vector<int> dp(N + 1, 1);	// ���� ���簡 �� �ڶ�� ������ �� �ִ� ���̸� ������ �迭

	for (int i = 1; i <= N; i++) {
		cin >> arr[i];	//������ �������� ���ʴ�� �Է¹���

		for (int j = i - 1; j > 0; j--) {// �ڽ��� �� ���縦 ���캻��
			if (arr[j] > arr[i]) {// �ڽ��� �� ���簡 �ڱ⺸�� ũ�ٸ�
				dp[i] = max(dp[i], dp[j] + 1); //������ dp[i]�� �ڱ⺸�� ū ���縦 ������ �� ������� + 1 �߿��� �� ū���� ä��
			}
		}
		maxLen = max(maxLen, dp[i]);	//��� ����
	}

	cout << N - maxLen;	// �����ؾ��ϴ� ���� ���

	return 0;
}