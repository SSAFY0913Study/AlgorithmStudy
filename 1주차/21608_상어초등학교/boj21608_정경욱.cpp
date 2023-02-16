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
	pair<int, int> priorityPair = make_pair(-1, -1);	// pair first - 좋아하는 학생의 수, second - 인접한 빈자리 수, 남은 자리가 근처에 좋아하는 학생이 없고 인접한 빈자리 수도 없을 경우를 위해 초기값을 둘다 음수로 설정
														// priorityPair는 좋아하는 학생의 수와 인접한 빈자리수를 고려하여 우선순위가 제일 높은 쌍을 저장한다.
	int resultX = 0, resultY = 0;	// resultX : 결과값 자리의 행, resultY: 결과값 자리의 열

	// 전체 map(2차원 배열)을 돌면서 조건에 부합하는 자리를 찾아보자, 동일한 조건일 경우에 행이 작은 것이 우선이므로 행-> 열 순으로 2차원배열 탐색을 진행하는 것을 선택함
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			if (map[i][j] != 0) continue;	// 이미 자리가 차있는 경우에는 continue
			int nearEmpty = 0;				// 비어있는 인접한 자리의 수 (동서남북에 모두 비어 있을경우 최대 4)
			int nearFavorit = 0;			// 인접한 자리에 위치한 좋아하는 사람의 수(동서남북에 모두 좋아하는 사람이 있을 경우 최대4)
			for (int k = 0; k < 4; k++) {	// 사방탐색을 하며 인접한 위치가 어떤지 살펴보자
				int nx = i + dx[k];			
				int ny = j + dy[k];

				if (nx > 0 && nx <= N && ny > 0 && ny <= N) {	// map의 범위를 넘어가지 않으면 진행한다.
					if (map[nx][ny] == 0) nearEmpty++;			// 인접한 자리가 비어있으면 비어있는 자리의 수를 1 더해준다.
					else {	//주변에 이미 학생이 있는 경우
						for (int h = 0; h < 4; h++) {	// 현재 학생의 친한친구를 탐색한다.
							if (graph[v][h] == map[nx][ny]) {	// 주변에 위치한 친구가 친한 친구이면 nearFavorit의 수 1증가
								nearFavorit++;
								break;
							}
						}
					}
				}
			}
			if (nearFavorit == 4) {	// 친한 친구가 4명이면 바로 자리를 설정해주고 함수를 끝낸다. 행과 열이 제일 낮기 때문이다.
				map[i][j] = v;
				return;
			}

			if (priorityPair.first <= nearFavorit) {	// 우선 순위pair가 가지고 있는 nearFavorit의 최대값과 현재의 nearFavorit을 비교하여 nearFavorit이 같거나 큰 경우만 살펴본다.
				if (priorityPair.first == nearFavorit && priorityPair.second < nearEmpty) {	//nearFavorit의 최대값은 같고 nearEmpty의 수가 priorityPair의 nearEmpty수보다 클 때 priorityPair가 변경된다.
					priorityPair.second = nearEmpty;	//priorityPair를 변경해준다.
					resultX = i;	// 학생이 위치할 low를 설정해준다.
					resultY = j;	// 학생이 위치할 column을 설정해준다.
				}
				else if (priorityPair.first < nearFavorit) {	// priorityPair의 nearFavorit보다 현재의 nearFavorit이 큰 경우 nearEmpty의 값과 상관없이 우선순위pair는 변경된다.
					priorityPair.first = nearFavorit;
					priorityPair.second = nearEmpty;
					resultX = i;	//학생이 위치할 low를 설정해준다.
					resultY = j;	//학생이 위치할 column을 설정해준다.
				}
			}
		}
	}
	map[resultX][resultY] = v;	// 정해진 학생의 자리를 map에 저장한다.
}

/*
 함수이름 : sumSatisfaction
 리턴값   : 위치가 정해진 후 총 학생들의 만족도 값의 합
 동작방식 : 2차원 배열을 전부 탐색하면서 동서남북에 친한 친구가 있으면 만족도 증가시켜준다.
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
		cin >> studentId;	//학생번호 입력받기

		for (int j = 0; j < 4; j++) {
			cin >> likeNum;	// 4명의 좋아하는 학생 입력받기
			graph[studentId].push_back(likeNum);	//좋아하는 학생들을 그래프에 넣기
		}

		checkNear(studentId);	//함수호출
	}

	cout << sumSatisfaction();

	return 0;
}