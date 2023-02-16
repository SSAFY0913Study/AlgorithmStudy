#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <deque>
using namespace std;

int n, w, L, times = 1, curWeight = 0;
vector<int> weight;
vector<int> bridge;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> n >> w >> L;

	weight = vector<int>(n + 1, 0);
	bridge = vector<int>(w + 1, 0);

	for (int i = 1; i <= n; i++) {
		cin >> weight[i];
	}

	bridge[1] = weight[1];	// �ٸ��� ù��° ������ Ʈ���� ���Ը� ���´�.
	curWeight += weight[1];
	times = 1;

	int curTruckIdx = 2;	// ���� Ʈ���� ��ȣ

	while (curTruckIdx <= n) {	// ������ Ʈ���� �ٸ��� �������� ������ ����
		times++; // �ð� ����

		curWeight -= bridge[w];

		for (int i = w - 1; i >= 1; i--) {
			bridge[i + 1] = bridge[i];	// Ʈ���� ��ĭ�� �̵�
		}

		if (curWeight + weight[curTruckIdx] <= L) {	// ���� �ٸ����� �ִ� Ʈ���� ���Կ� ���� Ʈ���� ������ ���� L���� ������ ���� Ʈ���� �ٸ��� �������� �ø�
			curWeight += weight[curTruckIdx];
			bridge[1] = weight[curTruckIdx];	// �ٸ��� �������� Ʈ���� �ø�
			curTruckIdx++;
		}
		else bridge[1] = 0;
	}

	cout << times + w;	// ������ Ʈ���� �ö� ������ �� �ٸ��� ���̸� ���� ���� �����

	return 0;
}