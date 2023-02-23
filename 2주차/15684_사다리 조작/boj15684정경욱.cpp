#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

int N, M, H, addCol = 4;
vector<pair<int, int>> canVisitLadder;	//�ʱ���¿��� �湮�� �� �ִ� ��ٸ��� ��ǥ �迭

bool ladder[32][11];

void simul(int select);
void combination(int cnt, int select);
int DFS(int low, int col);

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N >> M >> H;

	int a, b;
	for (int i = 0; i < M; i++) {
		cin >> a >> b;
		ladder[a - 1][b - 1] = true;	// ��ٸ� ����
	}

	for (int i = 0; i < H; i++) {
		for (int j = 0; j < N - 1; j++) {
			if (!ladder[i][j]) {	// ���ð����� ��ٸ� �迭�� �߰�
				if (j == 0 && !ladder[i][j + 1]) {
					canVisitLadder.push_back(make_pair(i, j));
				}
				else if (j == N - 2 && !ladder[i][j - 1]) {
					canVisitLadder.push_back(make_pair(i, j));
				}
				else if (!ladder[i][j + 1] && !ladder[i][j - 1]) {
					canVisitLadder.push_back(make_pair(i, j));
				}
			}
		}
	}

	combination(0, 0);

	if (addCol <= 3) cout << addCol;	//�߰��� ��ٸ��� ������ 3�� ������ ���
	else cout << -1;

	return 0;
}

void simul(int select) {//��ٸ�Ÿ�� �ùķ��̼�

	for (int i = 1; i <= N; i++) {
		if (i != DFS(1, i)) return;
	}
	addCol = min(select, addCol);
}

int DFS(int low, int col) {
	if (low == H + 1) {
		return col;
	}
	if (col - 1 >= 1 && ladder[low - 1][col - 2]) return DFS(low + 1, col - 1);
	else if (col + 1 <= N && ladder[low - 1][col - 1]) return DFS(low + 1, col + 1);
	else return DFS(low + 1, col);
}

void combination(int cnt, int select) {
	if (select >= addCol) return;	// ������ ��ٸ��� ������ ��������� ũ�ų� ���ٸ� ���̻� Ž���� �������� �ʴ´�.
	if (select == 3) {// ������ ���� 3����� ���̻� ������ ���������ʰ� �ùķ��̼��� ���� �� �����.
		simul(select);
		return;
	}
	if (cnt == canVisitLadder.size()) {	//�� ���ƺ� ��� �ùķ��̼��� ������ ����
		simul(select);	
		return;
	}

	int low = canVisitLadder[cnt].first;
	int col = canVisitLadder[cnt].second;
	if (!ladder[low][col]) {	// ���� Ž��
		combination(cnt + 1, select);	// ����  cnt�� ��ٸ��� �߰����� �ʰ� �������� ����
		if (col == 0 && !ladder[low][col + 1]) {// ��ٸ� �߰� �������� �˻�
			ladder[low][col] = true;
			combination(cnt + 1, select + 1);	// ��ٸ� �߰� �� ���� ��� ����
			ladder[low][col] = false;
		}
		else if (col == N - 2 && !ladder[low][col - 1]) {// ��ٸ� �߰� �������� �˻�
			ladder[low][col] = true;
			combination(cnt + 1, select + 1);	// ��ٸ� �߰� �� ���� ��� ����
			ladder[low][col] = false;
		}
		else if (!ladder[low][col + 1] && !ladder[low][col - 1]) {// ��ٸ� �߰� �������� �˻�
			ladder[low][col] = true;
			combination(cnt + 1, select + 1);	// ��ٸ� �߰� �� ���� ��� ����
			ladder[low][col] = false;
		}
	}
}