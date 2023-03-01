#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;


/* Ǯ�̹��
* 1. 3���� �迭�� ���� A, B, C���뿡 ��� ���� ���� ������ �湮�ߴ� ������ ������ üũ�Ѵ�.
* 2. ������ �湮�ߴ� ��ġ�� ���ȣ���� �������� �ʴ´�.
* 3. A������ ���� 0�϶� C�� ���� ������� �����Ѵ�.
*/

int capacity[4];
bool map[200][200][200];
vector<int> result;	//A������ ���� 0�϶� C ���뿡 �ִ� ���� ���� ������ �迭

void moveWater(int a, int b, int c) {// a= ���� A������ ��, b = ���� B������ ��, c = ���� C������ ��
	if (a == 0) result.push_back(c);	//A������ ����� �� C������ ���� ���� ��� �迭�� �߰�
	int moveAmount;	// �̵��� ���� ��
	if (a != 0) {//A���뿡�� B�� C�������� ���� �ű�� ���
		//1. A���뿡�� B�������� �ű�� ���
		moveAmount = min(a, capacity[2] - b);	// �̵��� ���� ���� A���뿡 �ִ� ���� ��� B������ �뷮 - ���� B���뿡 �ִ� ���� �� �� �ּҰ�
		if (!map[a - moveAmount][b + moveAmount][c]) {// ���� ������¸� �湮�� ���� ���ٸ� ��� ����
			map[a - moveAmount][b + moveAmount][c] = true;	//�湮üũ
			moveWater(a - moveAmount, b + moveAmount, c);
		}

		//2. A���뿡�� C�������� �ű�� ���
		moveAmount = min(a, capacity[3] - c);
		if (!map[a - moveAmount][b][c + moveAmount]) {
			map[a - moveAmount][b][c + moveAmount] = true;
			moveWater(a - moveAmount, b, c + moveAmount);
		}
	}
	if (b != 0) {
		//3. B���뿡�� A�������� �ű�� ���
		moveAmount = min(b, capacity[1] - a);
		if (!map[a + moveAmount][b - moveAmount][c]) {
			map[a + moveAmount][b - moveAmount][c] = true;
			moveWater(a + moveAmount, b - moveAmount, c);
		}
		//4. B���뿡�� C�������� �ű�� ���
		moveAmount = min(b, capacity[3] - c);
		if (!map[a][b - moveAmount][c + moveAmount]) {
			map[a][b - moveAmount][c + moveAmount] = true;
			moveWater(a, b - moveAmount, c + moveAmount);
		}
	}
	if (c != 0) {
		//5. C���뿡�� A�������� �ű�� ���
		moveAmount = min(c, capacity[1] - a);
		if (!map[a + moveAmount][b][c - moveAmount]) {
			map[a + moveAmount][b][c - moveAmount] = true;
			moveWater(a + moveAmount, b, c - moveAmount);
		}
		//6. C���뿡�� B�������� �ű�� ���
		moveAmount = min(c, capacity[2] - b);
		if (!map[a][b + moveAmount][c - moveAmount]) {
			map[a][b + moveAmount][c - moveAmount] = true;
			moveWater(a, b + moveAmount, c - moveAmount);
		}
	}
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	for (int i = 1; i <= 3; i++) {
		cin >> capacity[i];
	}

	map[0][0][capacity[3]] = true;
	moveWater(0, 0, capacity[3]);

	sort(result.begin(), result.end());	// ������� ������������ ����

	for (int i = 0; i < result.size(); i++) {
		cout << result[i] << ' ';
	}

	return 0;
}