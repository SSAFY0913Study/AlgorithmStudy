#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

int N, M, result = INT_MAX;
vector<int> arr;


int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N >> M;

	arr = vector<int>(N);

	for (int i = 0; i < N; i++) {
		cin >> arr[i];
	}

	sort(arr.begin(), arr.end()); //�Է¹��� ������ ������������ �����Ѵ�.

	/* Ǯ�̹�1 
	for (int i = 0; i < N - 1; i++) {	//ù��° ���Һ��� N - 1��° ���ұ��� �ݺ�
		for (int j = i + 1; j < N; j++) {//�ڽ��� ���� ���ҿ� ���� ���Ѵ�.
			if (arr[j] - arr[i] >= M) {// ���� ���ҿ� ���̰� M���� ũ�ų� �������� ���� �ݺ����� Ż���Ѵ�.
				if (arr[j] - arr[i] == M) {//���̰� M�̸� M�� �ּ��� ����̹Ƿ� ����� ����ϰ� �����Ѵ�.
					cout << M;
					return 0;
				}
				result = min(result, arr[j] - arr[i]);	// ���� �ּҰ��� ���Ͽ� ����� �����ϰ� �ݸ��� Ż���Ѵ�.
				break;
			}
		}
	}*/

	//Ǯ�̹�2 �̺�Ž���� Ȱ���� ���
	for (int i = 0; i < N - 1; i++) {
		int left = i + 1;
		int right = N - 1;
		int mid;	//mid�ε���

		while (left <= right) {
			mid = (left + right) / 2;
			if (arr[mid] - arr[i] > M) {//���̰� M���� ũ�� right��ġ ����
				result = min(result, arr[mid] - arr[i]);	//����� ����
				right = mid - 1;
			}
			else if (arr[mid] - arr[i] < M) {
				left = mid + 1;
			}
			else {	//���̰� M�ΰ�� ����� ����ϰ� ����
				cout << M;
				return 0;
			}
		}
	}

	cout << result;

	return 0;
}