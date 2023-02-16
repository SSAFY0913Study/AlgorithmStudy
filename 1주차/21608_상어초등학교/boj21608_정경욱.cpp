#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <deque>
using namespace std;

int map[21][21];
vector<int> graph[401];
int N;
int dx[4] = { 0, 1, 0, -1 };
int dy[4] = { 1, 0, -1, 0 };

void checkNear(int v) {
	pair<int, int> priorityPair = make_pair(-1, -1);	// pair first - �����ϴ� �л��� ��, second - ������ ���ڸ� ��, ���� �ڸ��� ��ó�� �����ϴ� �л��� ���� ������ ���ڸ� ���� ���� ��츦 ���� �ʱⰪ�� �Ѵ� ������ ����
														// priorityPair�� �����ϴ� �л��� ���� ������ ���ڸ����� ����Ͽ� �켱������ ���� ���� ���� �����Ѵ�.
	int resultX = 0, resultY = 0;	// resultX : ����� �ڸ��� ��, resultY: ����� �ڸ��� ��

	// ��ü map(2���� �迭)�� ���鼭 ���ǿ� �����ϴ� �ڸ��� ã�ƺ���, ������ ������ ��쿡 ���� ���� ���� �켱�̹Ƿ� ��-> �� ������ 2�����迭 Ž���� �����ϴ� ���� ������
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			if (map[i][j] != 0) continue;	// �̹� �ڸ��� ���ִ� ��쿡�� continue
			int nearEmpty = 0;				// ����ִ� ������ �ڸ��� �� (�������Ͽ� ��� ��� ������� �ִ� 4)
			int nearFavorit = 0;			// ������ �ڸ��� ��ġ�� �����ϴ� ����� ��(�������Ͽ� ��� �����ϴ� ����� ���� ��� �ִ�4)
			for (int k = 0; k < 4; k++) {	// ���Ž���� �ϸ� ������ ��ġ�� ��� ���캸��
				int nx = i + dx[k];			
				int ny = j + dy[k];

				if (nx > 0 && nx <= N && ny > 0 && ny <= N) {	// map�� ������ �Ѿ�� ������ �����Ѵ�.
					if (map[nx][ny] == 0) nearEmpty++;			// ������ �ڸ��� ��������� ����ִ� �ڸ��� ���� 1 �����ش�.
					else {	//�ֺ��� �̹� �л��� �ִ� ���
						for (int h = 0; h < 4; h++) {	// ���� �л��� ģ��ģ���� Ž���Ѵ�.
							if (graph[v][h] == map[nx][ny]) {	// �ֺ��� ��ġ�� ģ���� ģ�� ģ���̸� nearFavorit�� �� 1����
								nearFavorit++;
								break;
							}
						}
					}
				}
			}
			if (nearFavorit == 4) {	// ģ�� ģ���� 4���̸� �ٷ� �ڸ��� �������ְ� �Լ��� ������. ��� ���� ���� ���� �����̴�.
				map[i][j] = v;
				return;
			}

			if (priorityPair.first <= nearFavorit) {	// �켱 ����pair�� ������ �ִ� nearFavorit�� �ִ밪�� ������ nearFavorit�� ���Ͽ� nearFavorit�� ���ų� ū ��츸 ���캻��.
				if (priorityPair.first == nearFavorit && priorityPair.second < nearEmpty) {	//nearFavorit�� �ִ밪�� ���� nearEmpty�� ���� priorityPair�� nearEmpty������ Ŭ �� priorityPair�� ����ȴ�.
					priorityPair.second = nearEmpty;	//priorityPair�� �������ش�.
					resultX = i;	// �л��� ��ġ�� low�� �������ش�.
					resultY = j;	// �л��� ��ġ�� column�� �������ش�.
				}
				else if (priorityPair.first < nearFavorit) {	// priorityPair�� nearFavorit���� ������ nearFavorit�� ū ��� nearEmpty�� ���� ������� �켱����pair�� ����ȴ�.
					priorityPair.first = nearFavorit;
					priorityPair.second = nearEmpty;
					resultX = i;	//�л��� ��ġ�� low�� �������ش�.
					resultY = j;	//�л��� ��ġ�� column�� �������ش�.
				}
			}
		}
	}
	map[resultX][resultY] = v;	// ������ �л��� �ڸ��� map�� �����Ѵ�.
}

/*
 �Լ��̸� : sumSatisfaction
 ���ϰ�   : ��ġ�� ������ �� �� �л����� ������ ���� ��
 ���۹�� : 2���� �迭�� ���� Ž���ϸ鼭 �������Ͽ� ģ�� ģ���� ������ ������ ���������ش�.
*/
int sumSatisfaction() {
	int result = 0;
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			int sum = 0;
			for (int k = 0; k < 4; k++) {
				int nx = i + dx[k];
				int ny = j + dy[k];

				if (nx > 0 && nx <= N && ny > 0 && ny <= N) {
					for (int h = 0; h < 4; h++) {
						if (graph[map[i][j]][h] == map[nx][ny]) {
							if (sum == 0) sum = 1;
							else sum *= 10;
							break;
						}
					}
				}
			}
			result += sum;
		}
	}
	return result;
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N;

	int studentNum = N * N, studentId, likeNum;

	for (int i = 1; i <= studentNum; i++) {
		cin >> studentId;	//�л���ȣ �Է¹ޱ�

		for (int j = 0; j < 4; j++) {
			cin >> likeNum;	// 4���� �����ϴ� �л� �Է¹ޱ�
			graph[studentId].push_back(likeNum);	//�����ϴ� �л����� �׷����� �ֱ�
		}

		checkNear(studentId);	//�Լ�ȣ��
	}

	cout << sumSatisfaction();

	return 0;
}