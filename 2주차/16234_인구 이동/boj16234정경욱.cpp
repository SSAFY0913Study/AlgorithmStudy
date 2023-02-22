#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <queue>
#include <limits.h>
using namespace std;

int N, L, R, day = 0;
int map[50][50];
bool visited[50][50];
int dx[4] = { 0, 1, 0, -1 };
int dy[4] = { 1, 0, -1, 0 };
vector<pair<int, int>> selected;	// 국경선이 열린 나라들의 좌표 값 집합

int personSum = 0;	// 국경선이 열린 나라들의 인구 총합

void clearVisited();
void DFS(int low, int col);
bool IsMove();
void simul();

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N >> L >> R;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> map[i][j];
		}
	}

	simul();

	cout << day;

	return 0;
}

/*
* 함수 이름: simul
* 함수 역할: 1. IsMove() 함수가 true를 반환할 동안 day를 증가시키며 시뮬레이션 실행
*			 2. clearVisited() 함수를 호출하여 방문했던 나라들의 상태를 다시 false로 바꿔준다.
*/
void simul() {

	while (IsMove()) {	// 인구이동이 발생할 수 있는 동안 실행
		clearVisited();
		day++; //날짜의 증가
	}
}

/*
* 함수 이름: IsMove
* 반환값 : bool 타입의 값, 인구 이동이 진행 된 경우 true를 리턴하고 인구 이동이 진행되지 않은 경우 false를 리턴한다.
* 함수 역할: 국경선이 열려있을 경우 인구 이동을 실행한다.
*/
bool IsMove() {
	bool pn = false;	// 인구 이동이 진행되었는지 여부

	for (int i = 0; i < N; i++) {	// 전체의 맵(2차원 배열)을 탐색한다.
		for (int j = 0; j < N; j++) {
			personSum = 0;	//현재 위치의 나라와 국경선이 개방되어 있는 인구의 총합
			selected.clear();	// 이전에 남아있던 선택 배열 지우기

			if (visited[i][j]) continue;	// 현재 나라를 이미 방문했었다면 다음 진행

			personSum = map[i][j];	//	현재 위치 나라의 인구를 초기값으로 설정
			visited[i][j] = true; // 현재 위치 나라를 방문처리
			selected.push_back(make_pair(i, j));	// 현재 위치 나라를 선택배열에 추가
			
			DFS(i, j);	//현재 위치의 나라에서 국경선이 열려있는 나라를 탐색한다.

			if (selected.size() == 1) continue;	// 국경선이 열려있는 나라가 없다면, 즉 선택배열에는 현재 자기 자신만 있으므로 인구 이동이 진행되지 않은 것이므로 continue

			pn = true;	// 선택배열에 2개이상의 나라가 있다면 국경선이 열려있다는 뜻이므로 인구이동이 가능하고 인구 이동 진행여부를 true로 변경
			
			for (int k = 0; k < selected.size(); k++) {// 선택배열에 있는 나라들의 값을 변경해준다.
				int low = selected[k].first;
				int col = selected[k].second;

				map[low][col] = personSum / selected.size();	// 국경선이 열린 나라들의 인구 이동 진행시킴
			}
		}
	}

	return pn;	// 인구 이동 진행 여부를 반환
}

/*
* 함수이름: DFS
* 함수 역할: 깊이 우선 탐색을 통해 인구 이동이 가능한 인접한 나라들을 모두 찾고 방문처리한다.
*/
void DFS(int low, int col) {
	int nx;
	int ny;
	for (int i = 0; i < 4; i++) {
		nx = low + dx[i];	// 다음 행
		ny = col + dy[i];	// 다음 열

		if (nx >= 0 && nx < N && ny >= 0 && ny < N) {// 범위 조건 검사
			if (!visited[nx][ny] && abs(map[nx][ny] - map[low][col]) >= L && abs(map[nx][ny] - map[low][col]) <= R) {	// 인구 이동 가능 조건 검사
				visited[nx][ny] = true;	// 방문처리
				selected.push_back(make_pair(nx, ny));	// 선택 배열(인접한 나라 배열)에 위치 추가
				personSum += map[nx][ny]; // 국경선이 열려있는 인접한 나라들의 총 인구수를 증가
				DFS(nx, ny);
			}
		}
	}
}

void clearVisited() {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			visited[i][j] = false;
		}
	}
}