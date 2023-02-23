#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

int N, M, H, addCol = 4;
vector<pair<int, int>> canVisitLadder;	//초기상태에서 방문할 수 있는 사다리의 좌표 배열

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
		ladder[a - 1][b - 1] = true;	// 사다리 설정
	}

	for (int i = 0; i < H; i++) {
		for (int j = 0; j < N - 1; j++) {
			if (!ladder[i][j]) {	// 선택가능한 사다리 배열에 추가
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

	if (addCol <= 3) cout << addCol;	//추가한 사다리의 개수가 3개 이하일 경우
	else cout << -1;

	return 0;
}

void simul(int select) {//사다리타기 시뮬레이션

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
	if (select >= addCol) return;	// 선택한 사다리의 개수가 결과값보다 크거나 같다면 더이상 탐색을 진행하지 않는다.
	if (select == 3) {// 선택한 것이 3개라면 더이상 선택을 진행하지않고 시뮬레이션을 돌린 후 멈춘다.
		simul(select);
		return;
	}
	if (cnt == canVisitLadder.size()) {	//다 돌아본 경우 시뮬레이션을 돌린후 리턴
		simul(select);	
		return;
	}

	int low = canVisitLadder[cnt].first;
	int col = canVisitLadder[cnt].second;
	if (!ladder[low][col]) {	// 범위 탐색
		combination(cnt + 1, select);	// 현재  cnt의 사다리를 추가하지 않고 다음으로 진행
		if (col == 0 && !ladder[low][col + 1]) {// 사다리 추가 가능한지 검사
			ladder[low][col] = true;
			combination(cnt + 1, select + 1);	// 사다리 추가 후 다음 재귀 진행
			ladder[low][col] = false;
		}
		else if (col == N - 2 && !ladder[low][col - 1]) {// 사다리 추가 가능한지 검사
			ladder[low][col] = true;
			combination(cnt + 1, select + 1);	// 사다리 추가 후 다음 재귀 진행
			ladder[low][col] = false;
		}
		else if (!ladder[low][col + 1] && !ladder[low][col - 1]) {// 사다리 추가 가능한지 검사
			ladder[low][col] = true;
			combination(cnt + 1, select + 1);	// 사다리 추가 후 다음 재귀 진행
			ladder[low][col] = false;
		}
	}
}